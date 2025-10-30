'use client'
import { useMemo, useRef, useState } from 'react';
import styles from './table.module.css';

export type SalePoint = { date: string | Date; value: number }

type P = { data?: SalePoint[] }

export default function Table({ data }: P) {
  const base = (data && data.length ? data : DEMO_DATA)

  // Normaliza e ordena
  const points = useMemo(() => {
    return base.map(p => {
      const d = p.date instanceof Date ? p.date : new Date(p.date)
      return { x: d.getTime(), y: Number(p.value), d }
    }).sort((a, b) => a.x - b.x)
  }, [base])

  // Dimensões do gráfico
  const width = 800      // viewBox virtual (responsivo por CSS)
  const height = 320
  const m = { top: 16, right: 20, bottom: 36, left: 48 }
  const iw = width - m.left - m.right
  const ih = height - m.top - m.bottom

  // Escalas
  const xMin = points[0]?.x ?? 0
  const xMax = points[points.length - 1]?.x ?? 1
  const yMin = 0
  const yMax = Math.max(1, Math.max(...points.map(p => p.y)))

  const sx = (t: number) => m.left + ((t - xMin) / (xMax - xMin || 1)) * iw
  const sy = (v: number) => m.top + (1 - (v - yMin) / (yMax - yMin || 1)) * ih

  // Ticks simples
  const xTicks = getTimeTicks(xMin, xMax, 6)
  const yTicks = getLinearTicks(yMin, yMax, 5)

  // Linha (path)
  const dPath = useMemo(() => {
    if (!points.length) return ''
    return points.map((p, i) => `${i ? 'L' : 'M'} ${sx(p.x)} ${sy(p.y)}`).join(' ')
  }, [points])

  // Interação / tooltip
  const [hoverIdx, setHoverIdx] = useState<number | null>(null)
  const svgRef = useRef<SVGSVGElement>(null)

  function onMove(e: React.MouseEvent<SVGSVGElement>) {
    if (!svgRef.current || !points.length) return
    const rect = svgRef.current.getBoundingClientRect()
    const px = e.clientX - rect.left
    // converte px -> tempo (x)
    const t = xMin + ((px - m.left) / (iw || 1)) * (xMax - xMin)
    // acha o ponto mais próximo
    let best = 0; let bestDist = Infinity
    for (let i = 0; i < points.length; i++) {
      const dx = Math.abs(points[i].x - t)
      if (dx < bestDist) { bestDist = dx; best = i }
    }
    setHoverIdx(best)
  }
  function onLeave() { setHoverIdx(null) }

  const hovered = hoverIdx != null ? points[hoverIdx] : null

  const total = useMemo(() => points.reduce((a, c) => a + c.y, 0), [points])

  return (
    <div className={styles.container} data-cy="sales-linechart">
      <div className={styles.header}>
        <h2 className={styles.title}>Histórico de vendas</h2>
        <div className={styles.kpis}>
          <span className={styles.kpi}><strong>Total:</strong> {total.toLocaleString('pt-BR')}</span>
          <span className={styles.kpi}><strong>Pontos:</strong> {points.length}</span>
        </div>
      </div>

      <div className={styles.chartWrap}>
        <svg
          ref={svgRef}
          viewBox={`0 0 ${width} ${height}`}
          className={styles.svg}
          role="img"
          aria-label="Gráfico de linhas do histórico de vendas"
          onMouseMove={onMove}
          onMouseLeave={onLeave}
        >
          {/* Grid X */}
          {xTicks.map((t, i) => (
            <line key={`gx-${i}`} x1={sx(t)} x2={sx(t)} y1={m.top} y2={height - m.bottom} className={styles.grid}/>
          ))}
          {/* Grid Y */}
          {yTicks.map((v, i) => (
            <line key={`gy-${i}`} x1={m.left} x2={width - m.right} y1={sy(v)} y2={sy(v)} className={styles.grid}/>
          ))}

          {/* Eixos */}
          <line x1={m.left} x2={width - m.right} y1={height - m.bottom} y2={height - m.bottom} className={styles.axis}/>
          <line x1={m.left} x2={m.left} y1={m.top} y2={height - m.bottom} className={styles.axis}/>

          {/* Labels X */}
          {xTicks.map((t, i) => (
            <text key={`xt-${i}`} x={sx(t)} y={height - m.bottom + 22} className={styles.tick} textAnchor="middle">
              {formatDateBR(new Date(t))}
            </text>
          ))}
          {/* Labels Y */}
          {yTicks.map((v, i) => (
            <text key={`yt-${i}`} x={m.left - 8} y={sy(v)} className={styles.tick} textAnchor="end" dominantBaseline="middle">
              {v.toLocaleString('pt-BR')}
            </text>
          ))}

          {/* Linha principal */}
          <path d={dPath} className={styles.line} fill="none"/>

          {/* Pontos */}
          {points.map((p, i) => (
            <circle key={i} cx={sx(p.x)} cy={sy(p.y)} r={2.5} className={styles.dot}/>
          ))}

          {/* Hover guia/tooltip */}
          {hovered && (
            <>
              <line x1={sx(hovered.x)} x2={sx(hovered.x)} y1={m.top} y2={height - m.bottom} className={styles.hoverLine}/>
              <circle cx={sx(hovered.x)} cy={sy(hovered.y)} r={4.5} className={styles.hoverDot}/>
              {/* Tooltip básica */}
              <g transform={`translate(${Math.min(width - 180, Math.max(m.left, sx(hovered.x) + 8))}, ${m.top + 8})`}>
                <rect width="170" height="50" rx="8" className={styles.tooltipBg}/>
                <text x={10} y={20} className={styles.tooltipText}>
                  {formatDateFullBR(hovered.d)}
                </text>
                <text x={10} y={38} className={styles.tooltipText}>
                  Vendas: {hovered.y.toLocaleString('pt-BR')}
                </text>
              </g>
            </>
          )}
        </svg>
      </div>

      <p className={styles.hint}>
        Passe o mouse para ver os valores. Para usar dados reais, passe a prop <code>data</code> com <code>{`{ date, value }`}</code>.
      </p>
    </div>
  )
}

/* ================= Utilities ================= */
function formatDateBR(d: Date) {
  return d.toLocaleDateString('pt-BR', { day: '2-digit', month: '2-digit' })
}
function formatDateFullBR(d: Date) {
  return d.toLocaleDateString('pt-BR', { day: '2-digit', month: 'long', year: 'numeric' })
}
function getLinearTicks(min: number, max: number, count: number) {
  const span = max - min || 1
  const step = niceStep(span / (count - 1))
  const start = Math.ceil(min / step) * step
  const ticks: number[] = []
  for (let v = start; v <= max + 1e-6; v += step) ticks.push(Math.round(v))
  return ticks
}
function niceStep(raw: number) {
  const pow10 = Math.pow(10, Math.floor(Math.log10(raw)))
  const d = raw / pow10
  const step = d >= 5 ? 5 : d >= 2 ? 2 : 1
  return step * pow10
}
function getTimeTicks(min: number, max: number, count: number) {
  const span = Math.max(1, max - min)
  const step = Math.round(span / (count - 1))
  const ticks: number[] = []
  for (let i = 0; i < count; i++) ticks.push(min + i * step)
  return ticks
}

/* ================= Mock (remova em prod) ================= */
const DEMO_DATA: SalePoint[] = Array.from({ length: 14 }).map((_, i) => {
  const d = new Date()
  d.setDate(d.getDate() - (13 - i))
  return { date: d, value: Math.floor(200 + Math.random() * 900) }
})
