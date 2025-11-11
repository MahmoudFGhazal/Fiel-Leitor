'use client';
import { useMemo, useRef, useState } from 'react';
import styles from './table.module.css';

// Tipagem real da venda
export type SaleResponse = {
  id: number;
  createdAt: string;
  deliveryDate: string;
  freight: number;
  saleBooks: { price: number; quantity: number }[];
  statusSale?: { status: string };
};

type P = { data?: SaleResponse[] };

export default function Table({ data }: P) {
  const [mode, setMode] = useState<'daily' | 'monthly'>('daily');

  // 🔹 Converter vendas em pontos do gráfico
  const points = useMemo(() => {
    if (!data?.length) return [];

    // agrupa e soma por data
    const map = new Map<string, number>();
    for (const sale of data) {
      const date = new Date(sale.deliveryDate || sale.createdAt);
      const key =
        mode === 'monthly'
          ? `${date.getFullYear()}-${String(date.getMonth() + 1).padStart(2, '0')}`
          : date.toISOString().slice(0, 10);

      const booksTotal = sale.saleBooks?.reduce(
        (sum, b) => sum + (Number(b.price) || 0) * (Number(b.quantity) || 0),
        0
      ) ?? 0;

      const total = booksTotal + (sale.freight ?? 0);
      map.set(key, (map.get(key) || 0) + total);
    }

    return Array.from(map.entries())
      .map(([date, value]) => ({
        date,
        value,
        x: new Date(date).getTime(),
        d: new Date(date),
      }))
      .sort((a, b) => a.x - b.x);
  }, [data, mode]);

  // 🔹 Configuração do gráfico
  const width = 800, height = 320;
  const m = { top: 16, right: 20, bottom: 36, left: 48 };
  const iw = width - m.left - m.right;
  const ih = height - m.top - m.bottom;
  const xMin = points[0]?.x ?? 0;
  const xMax = points[points.length - 1]?.x ?? 1;
  const yMin = 0;
  const yMax = Math.max(1, Math.max(...points.map(p => p.value)));

  const sx = (t: number) => m.left + ((t - xMin) / (xMax - xMin || 1)) * iw;
  const sy = (v: number) => m.top + (1 - (v - yMin) / (yMax - yMin || 1)) * ih;

  const dPath = useMemo(() => {
    if (!points.length) return '';
    return points.map((p, i) => `${i ? 'L' : 'M'} ${sx(p.x)} ${sy(p.value)}`).join(' ');
  }, [points]);

  const [hoverIdx, setHoverIdx] = useState<number | null>(null);
  const svgRef = useRef<SVGSVGElement>(null);

  function onMove(e: React.MouseEvent<SVGSVGElement>) {
    if (!svgRef.current || !points.length) return;
    const rect = svgRef.current.getBoundingClientRect();
    const px = e.clientX - rect.left;
    const t = xMin + ((px - m.left) / iw) * (xMax - xMin);
    let best = 0, bestDist = Infinity;
    for (let i = 0; i < points.length; i++) {
      const dx = Math.abs(points[i].x - t);
      if (dx < bestDist) { bestDist = dx; best = i; }
    }
    setHoverIdx(best);
  }
  function onLeave() { setHoverIdx(null); }

  const hovered = hoverIdx != null ? points[hoverIdx] : null;
  const total = points.reduce((a, c) => a + c.value, 0);

  return (
    <div className={styles.container}>
      <div className={styles.header}>
        <h2 className={styles.title}>Histórico de vendas</h2>
        <div className={styles.kpis}>
          <span><strong>Total:</strong> {total.toLocaleString('pt-BR')}</span>
          <span><strong>Pontos:</strong> {points.length}</span>
        </div>
        <div className={styles.toggle}>
          <button
            className={mode === 'daily' ? styles.active : ''}
            onClick={() => setMode('daily')}
          >
            Por dia
          </button>
          <button
            className={mode === 'monthly' ? styles.active : ''}
            onClick={() => setMode('monthly')}
          >
            Por mês
          </button>
        </div>
      </div>

      <div className={styles.chartWrap}>
        <svg
          ref={svgRef}
          viewBox={`0 0 ${width} ${height}`}
          className={styles.svg}
          onMouseMove={onMove}
          onMouseLeave={onLeave}
        >
          <path d={dPath} className={styles.line} fill="none" />
          {points.map((p, i) => (
            <circle key={i} cx={sx(p.x)} cy={sy(p.value)} r={2.5} className={styles.dot} />
          ))}

          {hovered && (
            <>
              <line x1={sx(hovered.x)} x2={sx(hovered.x)} y1={m.top} y2={height - m.bottom} className={styles.hoverLine}/>
              <circle cx={sx(hovered.x)} cy={sy(hovered.value)} r={4.5} className={styles.hoverDot}/>
              <g transform={`translate(${Math.min(width - 180, Math.max(m.left, sx(hovered.x) + 8))}, ${m.top + 8})`}>
                <rect width="170" height="50" rx="8" className={styles.tooltipBg}/>
                <text x={10} y={20} className={styles.tooltipText}>
                  {hovered.d.toLocaleDateString('pt-BR', mode === 'monthly'
                    ? { month: 'long', year: 'numeric' }
                    : { day: '2-digit', month: 'short', year: 'numeric' })}
                </text>
                <text x={10} y={38} className={styles.tooltipText}>
                  Vendas: {hovered.value.toLocaleString('pt-BR')}
                </text>
              </g>
            </>
          )}
        </svg>
      </div>
    </div>
  );
}
