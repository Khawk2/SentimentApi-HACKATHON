import { useEffect, useState } from 'react'
import { sentimentApi, StatsResponse } from '../services/api'
import { Loader2, TrendingUp, TrendingDown, Minus, AlertCircle } from 'lucide-react'
import {
  BarChart,
  Bar,
  PieChart,
  Pie,
  Cell,
  XAxis,
  YAxis,
  CartesianGrid,
  Tooltip,
  Legend,
  ResponsiveContainer,
} from 'recharts'

const StatsDashboard = () => {
  const [stats, setStats] = useState<StatsResponse | null>(null)
  const [loading, setLoading] = useState(true)
  const [error, setError] = useState<string | null>(null)

  const fetchStats = async () => {
    setLoading(true)
    setError(null)
    try {
      const data = await sentimentApi.getStats()
      setStats(data)
    } catch (err: any) {
      setError(
        err.response?.data?.message ||
          'Error al cargar las estadísticas. Por favor, intenta de nuevo.'
      )
    } finally {
      setLoading(false)
    }
  }

  useEffect(() => {
    fetchStats()
    const interval = setInterval(fetchStats, 10000) // Actualizar cada 10 segundos
    return () => clearInterval(interval)
  }, [])

  const pieData = stats
    ? [
        {
          name: 'Positivos',
          value: stats.positivos,
          percentage: stats.porcentaje_positivos,
          color: '#10b981',
        },
        {
          name: 'Negativos',
          value: stats.negativos,
          percentage: stats.porcentaje_negativos,
          color: '#ef4444',
        },
        {
          name: 'Neutros',
          value: stats.neutros,
          percentage: stats.porcentaje_neutros,
          color: '#6b7280',
        },
      ]
    : []

  const barData = stats
    ? [
        {
          name: 'Positivos',
          Cantidad: stats.positivos,
          Porcentaje: stats.porcentaje_positivos,
        },
        {
          name: 'Negativos',
          Cantidad: stats.negativos,
          Porcentaje: stats.porcentaje_negativos,
        },
        {
          name: 'Neutros',
          Cantidad: stats.neutros,
          Porcentaje: stats.porcentaje_neutros,
        },
      ]
    : []

  if (loading) {
    return (
      <div className="flex items-center justify-center h-64">
        <Loader2 className="animate-spin text-blue-600" size={48} />
      </div>
    )
  }

  if (error) {
    return (
      <div className="bg-red-50 border border-red-200 rounded-lg p-6 flex items-start gap-3">
        <AlertCircle className="text-red-600 flex-shrink-0 mt-0.5" size={24} />
        <div>
          <h3 className="text-red-800 font-medium text-lg">Error</h3>
          <p className="text-red-700 mt-1">{error}</p>
          <button
            onClick={fetchStats}
            className="mt-4 bg-red-600 hover:bg-red-700 text-white px-4 py-2 rounded-lg transition-colors"
          >
            Reintentar
          </button>
        </div>
      </div>
    )
  }

  if (!stats || stats.total === 0) {
    return (
      <div className="bg-white rounded-xl shadow-lg p-8 border border-slate-200 text-center">
        <p className="text-slate-600 text-lg">
          No hay datos disponibles todavía.
        </p>
        <p className="text-slate-500 mt-2">
          Analiza algunos textos para ver las estadísticas aquí.
        </p>
      </div>
    )
  }

  return (
    <div className="space-y-6">
      {/* Summary Cards */}
      <div className="grid grid-cols-1 md:grid-cols-4 gap-4">
        <div className="bg-white rounded-xl shadow-lg p-6 border border-slate-200">
          <div className="flex items-center justify-between">
            <div>
              <p className="text-sm font-medium text-slate-600">Total</p>
              <p className="text-3xl font-bold text-slate-800 mt-2">
                {stats.total}
              </p>
            </div>
            <div className="bg-blue-100 p-3 rounded-lg">
              <TrendingUp className="text-blue-600" size={24} />
            </div>
          </div>
        </div>

        <div className="bg-white rounded-xl shadow-lg p-6 border border-slate-200">
          <div className="flex items-center justify-between">
            <div>
              <p className="text-sm font-medium text-slate-600">Positivos</p>
              <p className="text-3xl font-bold text-green-600 mt-2">
                {stats.positivos}
              </p>
              <p className="text-xs text-slate-500 mt-1">
                {stats.porcentaje_positivos.toFixed(1)}%
              </p>
            </div>
            <div className="bg-green-100 p-3 rounded-lg">
              <TrendingUp className="text-green-600" size={24} />
            </div>
          </div>
        </div>

        <div className="bg-white rounded-xl shadow-lg p-6 border border-slate-200">
          <div className="flex items-center justify-between">
            <div>
              <p className="text-sm font-medium text-slate-600">Negativos</p>
              <p className="text-3xl font-bold text-red-600 mt-2">
                {stats.negativos}
              </p>
              <p className="text-xs text-slate-500 mt-1">
                {stats.porcentaje_negativos.toFixed(1)}%
              </p>
            </div>
            <div className="bg-red-100 p-3 rounded-lg">
              <TrendingDown className="text-red-600" size={24} />
            </div>
          </div>
        </div>

        <div className="bg-white rounded-xl shadow-lg p-6 border border-slate-200">
          <div className="flex items-center justify-between">
            <div>
              <p className="text-sm font-medium text-slate-600">Neutros</p>
              <p className="text-3xl font-bold text-gray-600 mt-2">
                {stats.neutros}
              </p>
              <p className="text-xs text-slate-500 mt-1">
                {stats.porcentaje_neutros.toFixed(1)}%
              </p>
            </div>
            <div className="bg-gray-100 p-3 rounded-lg">
              <Minus className="text-gray-600" size={24} />
            </div>
          </div>
        </div>
      </div>

      {/* Charts */}
      <div className="grid grid-cols-1 lg:grid-cols-2 gap-6">
        {/* Pie Chart */}
        <div className="bg-white rounded-xl shadow-lg p-6 border border-slate-200">
          <h3 className="text-lg font-semibold text-slate-800 mb-4">
            Distribución de Sentimientos
          </h3>
          <ResponsiveContainer width="100%" height={300}>
            <PieChart>
              <Pie
                data={pieData}
                cx="50%"
                cy="50%"
                labelLine={false}
                label={({ name, percentage }) =>
                  `${name}: ${percentage.toFixed(1)}%`
                }
                outerRadius={100}
                fill="#8884d8"
                dataKey="value"
              >
                {pieData.map((entry, index) => (
                  <Cell key={`cell-${index}`} fill={entry.color} />
                ))}
              </Pie>
              <Tooltip />
              <Legend />
            </PieChart>
          </ResponsiveContainer>
        </div>

        {/* Bar Chart */}
        <div className="bg-white rounded-xl shadow-lg p-6 border border-slate-200">
          <h3 className="text-lg font-semibold text-slate-800 mb-4">
            Comparación de Sentimientos
          </h3>
          <ResponsiveContainer width="100%" height={300}>
            <BarChart data={barData}>
              <CartesianGrid strokeDasharray="3 3" />
              <XAxis dataKey="name" />
              <YAxis />
              <Tooltip />
              <Legend />
              <Bar dataKey="Cantidad" fill="#3b82f6" />
            </BarChart>
          </ResponsiveContainer>
        </div>
      </div>

      {/* Refresh Button */}
      <div className="flex justify-center">
        <button
          onClick={fetchStats}
          className="bg-blue-600 hover:bg-blue-700 text-white font-medium py-2 px-6 rounded-lg transition-colors shadow-md"
        >
          Actualizar Estadísticas
        </button>
      </div>
    </div>
  )
}

export default StatsDashboard

