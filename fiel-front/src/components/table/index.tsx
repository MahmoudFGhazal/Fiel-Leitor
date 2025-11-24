'use client';

import { SaleResponse } from '@/api/dtos/responseDTOs';
import { getCategories } from '@/utils/getTypes';
import { useEffect, useMemo, useState } from 'react';
import styles from './table.module.css';

type Props = { data?: SaleResponse[] };

type CategoryLine = {
  category: string;
  points: { x: number; y: number; month: string; d: Date }[];
  color: string;
};

const COLORS = [
  '#4285F4', '#DB4437', '#F4B400', '#0F9D58', '#AB47BC',
  '#00ACC1', '#FF7043', '#9E9D24', '#5C6BC0', '#26A69A',
];

// Mês abreviado
const monthNames: Record<number, string> = {
  1: 'jan', 2: 'fev', 3: 'mar', 4: 'abr', 5: 'mai', 6: 'jun',
  7: 'jul', 8: 'ago', 9: 'set', 10: 'out', 11: 'nov', 12: 'dez',
};

function formatMonth(key: string) {
  const [year, month] = key.split('-').map(Number);
  return `${monthNames[month]}/${String(year).slice(2)}`;
}

function safeDate(raw: any): Date {
  if (!raw) return new Date();
  if (raw instanceof Date) return raw;
  return new Date(raw);
}

function getMonthKey(d: Date) {
  return `${d.getFullYear()}-${String(d.getMonth() + 1).padStart(2, '0')}`;
}

function generateMonthRange(start: Date, end: Date): string[] {
  const arr: string[] = [];
  const cur = new Date(start.getFullYear(), start.getMonth(), 1);

  while (cur <= end) {
    arr.push(getMonthKey(cur));
    cur.setMonth(cur.getMonth() + 1);
  }

  return arr;
}

function generateSmartMonths(filteredData: SaleResponse[], startDate: string, endDate: string) {
  if (startDate && endDate) {
    const s = new Date(startDate);
    const e = new Date(endDate);

    return generateMonthRange(
      new Date(s.getFullYear(), s.getMonth(), 1),
      new Date(e.getFullYear(), e.getMonth(), 1)
    );
  }

  const allDates = filteredData.map((s) => safeDate(s.createdAt ?? s.deliveryDate));

  const minD = new Date(Math.min(...allDates.map((d) => d.getTime())));
  const maxD = new Date(Math.max(...allDates.map((d) => d.getTime())));

  const forcedMin = new Date(maxD);
  forcedMin.setMonth(maxD.getMonth() - 4);

  const realMin = minD < forcedMin ? minD : forcedMin;

  return generateMonthRange(realMin, maxD);
}

export default function Table({ data }: Props) {
  const [categories, setCategories] = useState<{ id: number; category: string }[]>([]);
  const [selectedCats, setSelectedCats] = useState<number[]>([]);
  const [startDate, setStartDate] = useState('');
  const [endDate, setEndDate] = useState('');

  useEffect(() => {
    getCategories().then((cats) => {
      setCategories(
        cats
          .filter((c) => c.id && c.category)
          .map((c) => ({ id: c.id!, category: c.category! }))
      );
    });
  }, []);

  const filteredData = useMemo(() => {
    if (!data) return [];

    return data.filter((sale) => {
      const d = safeDate(sale.createdAt ?? sale.deliveryDate);

      if (startDate && d < new Date(startDate)) return false;
      if (endDate && d > new Date(endDate)) return false;

      return true;
    });
  }, [data, startDate, endDate]);

  const lines: CategoryLine[] = useMemo(() => {
    if (!filteredData.length) return [];

    const allMonths = generateSmartMonths(filteredData, startDate, endDate);

    // Todas as categorias
    if (selectedCats.length === 0) {
      const map = new Map(allMonths.map((m) => [m, 0]));

      for (const sale of filteredData) {
        const d = safeDate(sale.createdAt ?? sale.deliveryDate);
        const month = getMonthKey(d);

        const booksTotal = sale.saleBooks?.reduce(
          (sum, b) => sum + (b.price || 0) * (b.quantity || 0),
          0
        ) ?? 0;

        const total = booksTotal + (sale.freight || 0);

        map.set(month, (map.get(month) || 0) + total);
      }

      return [
        {
          category: 'Todas as Categorias',
          color: COLORS[0],
          points: [...map.entries()].map(([m, y]) => {
            const d = new Date(m + '-01');
            return { month: m, y, d, x: d.getTime() };
          }),
        },
      ];
    }

    // Categorias específicas
    return selectedCats.map((catId, idx) => {
      const catName = categories.find((c) => c.id === catId)?.category ?? 'Categoria';
      const map = new Map(allMonths.map((m) => [m, 0]));

      for (const sale of filteredData) {
        const d = safeDate(sale.deliveryDate ?? sale.createdAt);
        const month = getMonthKey(d);

        const total =
          sale.saleBooks
            ?.filter((b) => b.book?.categories?.some((c) => c.id === catId))
            .reduce((sum, b) => sum + (b.price || 0) * (b.quantity || 0), 0) ?? 0;

        map.set(month, (map.get(month) || 0) + total);
      }

      return {
        category: catName,
        color: COLORS[idx % COLORS.length],
        points: [...map.entries()].map(([m, y]) => {
          const d = new Date(m + '-01');
          return { month: m, y, d, x: d.getTime() };
        }),
      };
    });
  }, [filteredData, selectedCats, categories, startDate, endDate]);

  const allPts = lines.flatMap((l) => l.points);
  const yMax = Math.max(...allPts.map((p) => p.y), 1);

  const width = 900, height = 380;
  const m = { top: 40, right: 100, bottom: 70, left: 70 };

  const MIN_SPACING = 70;

  const sx = (index: number) => m.left + index * MIN_SPACING;
  const sy = (y: number) =>
    m.top + (1 - y / yMax) * (height - m.top - m.bottom);

  return (
    <div className={styles.container}>

      {/* FILTROS */}
      <div className={styles.filters}>
        <div>
          <label>Início:</label>
          <input type="date" value={startDate} onChange={(e) => setStartDate(e.target.value)} />
        </div>

        <div>
          <label>Fim:</label>
          <input type="date" value={endDate} onChange={(e) => setEndDate(e.target.value)} />
        </div>

        <div>
          <label>Categorias:</label>
          <select
            multiple
            size={7}
            onChange={() => {}}
            onMouseDown={(e) => {
              e.preventDefault();
              const opt = e.target as HTMLOptionElement;
              const id = Number(opt.value);

              setSelectedCats((prev) =>
                prev.includes(id)
                  ? prev.filter((c) => c !== id)
                  : [...prev, id]
              );
            }}
          >
            {categories.map((c) => (
              <option
                key={c.id}
                value={c.id}
                style={{
                  fontWeight: selectedCats.includes(c.id) ? 'bold' : 'normal',
                  background: selectedCats.includes(c.id) ? '#dcecff' : 'white',
                }}
              >
                {c.category}
              </option>
            ))}
          </select>
        </div>
      </div>

      {/* GRÁFICO */}
      <svg viewBox={`0 0 ${width} ${height}`} className={styles.svg}>
        {/* EIXO Y */}
        {[0, yMax / 2, yMax].map((v) => (
          <g key={v}>
            <text x={m.left - 10} y={sy(v) + 5} fontSize="13" textAnchor="end">
              {v.toFixed(0)}
            </text>
            <line
              x1={m.left}
              x2={width - m.right}
              y1={sy(v)}
              y2={sy(v)}
              stroke="#ccc"
              strokeDasharray="4"
            />
          </g>
        ))}

        {/* EIXO X */}
        {lines[0]?.points.map((p, index) => (
          <g key={p.month}>
            <text x={sx(index)} y={height - 12} fontSize="13" textAnchor="middle">
              {formatMonth(p.month)}
            </text>
          </g>
        ))}

        {/* LINHAS */}
        {lines.map((l) => {
          const path = l.points
            .map((p, i) => `${i === 0 ? 'M' : 'L'} ${sx(i)} ${sy(p.y)}`)
            .join(' ');

          return <path key={l.category} d={path} stroke={l.color} fill="none" strokeWidth={2} />;
        })}
      </svg>

      {/* LEGENDA EM GRADE */}
      <div className={styles.legendGrid}>
        {lines.map((l) => (
          <div key={l.category} className={styles.legendItem}>
            <span className={styles.colorDot} style={{ background: l.color }}></span>
            {l.category}
          </div>
        ))}
      </div>
    </div>
  );
}
