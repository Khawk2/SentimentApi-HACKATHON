import { useState } from 'react'
import { sentimentApi, SentimentResponse } from '../services/api'
import { Loader2, Send, AlertCircle, CheckCircle2 } from 'lucide-react'

const SentimentAnalyzer = () => {
  const [text, setText] = useState('')
  const [result, setResult] = useState<SentimentResponse | null>(null)
  const [loading, setLoading] = useState(false)
  const [error, setError] = useState<string | null>(null)

  const handleAnalyze = async () => {
    if (!text.trim()) {
      setError('Por favor, ingresa un texto para analizar')
      return
    }

    setLoading(true)
    setError(null)
    setResult(null)

    try {
      const response = await sentimentApi.analyzeSentiment(text)
      setResult(response)
    } catch (err: any) {
      setError(
        err.response?.data?.message ||
          'Error al analizar el sentimiento. Por favor, intenta de nuevo.'
      )
    } finally {
      setLoading(false)
    }
  }

  const handleKeyPress = (e: React.KeyboardEvent) => {
    if (e.key === 'Enter' && e.ctrlKey) {
      handleAnalyze()
    }
  }

  // MODIFICADO: Agregué las variantes dark para los colores de las etiquetas
  const getSentimentColor = (sentiment: string) => {
    switch (sentiment) {
      case 'Positivo':
        return 'bg-green-100 text-green-800 border-green-300 dark:bg-green-900/30 dark:text-green-400 dark:border-green-800'
      case 'Negativo':
        return 'bg-red-100 text-red-800 border-red-300 dark:bg-red-900/30 dark:text-red-400 dark:border-red-800'
      case 'Neutro':
        return 'bg-gray-100 text-gray-800 border-gray-300 dark:bg-slate-700 dark:text-slate-300 dark:border-slate-600'
      default:
        return 'bg-gray-100 text-gray-800 border-gray-300 dark:bg-slate-700 dark:text-slate-300 dark:border-slate-600'
    }
  }

  const getSentimentIcon = (sentiment: string) => {
    switch (sentiment) {
      case 'Positivo':
        return '😊'
      case 'Negativo':
        return '😞'
      case 'Neutro':
        return '😐'
      default:
        return '🤔'
    }
  }

  const getProbabilityColor = (prob: number) => {
    if (prob >= 0.7) return 'text-green-600 dark:text-green-400'
    if (prob >= 0.5) return 'text-yellow-600 dark:text-yellow-400'
    return 'text-orange-600 dark:text-orange-400'
  }

  return (
    <div className="space-y-6">
      {/* Input Section */}
      {/* Agregamos dark:bg-slate-800 dark:border-slate-700 */}
      <div className="bg-white rounded-xl shadow-lg p-6 border border-slate-200 dark:bg-slate-800 dark:border-slate-700 transition-colors">
        <label
          htmlFor="text-input"
          // Agregamos dark:text-slate-300
          className="block text-sm font-medium text-slate-700 mb-2 dark:text-slate-300"
        >
          Ingresa el texto a analizar
        </label>
        <textarea
          id="text-input"
          value={text}
          onChange={(e) => setText(e.target.value)}
          onKeyDown={handleKeyPress}
          placeholder="Escribe aquí el texto que deseas analizar... Ejemplo: 'Excelente servicio, muy recomendado'"
          // Agregamos dark:bg-slate-900 dark:border-slate-600 dark:text-white dark:placeholder-slate-500
          className="w-full h-32 px-4 py-3 border border-slate-300 rounded-lg focus:ring-2 focus:ring-blue-500 focus:border-transparent resize-none dark:bg-slate-900 dark:border-slate-600 dark:text-white dark:placeholder-slate-500"
          disabled={loading}
        />
        <div className="mt-3 flex items-center justify-between">
          <p className="text-xs text-slate-500 dark:text-slate-400">
            Presiona Ctrl + Enter para analizar
          </p>
          <p className="text-xs text-slate-500 dark:text-slate-400">{text.length} caracteres</p>
        </div>
        <button
          onClick={handleAnalyze}
          disabled={loading || !text.trim()}
          className="mt-4 w-full bg-blue-600 hover:bg-blue-700 disabled:bg-slate-400 disabled:cursor-not-allowed text-white font-medium py-3 px-6 rounded-lg transition-colors flex items-center justify-center gap-2 shadow-md dark:disabled:bg-slate-700 dark:disabled:text-slate-500"
        >
          {loading ? (
            <>
              <Loader2 className="animate-spin" size={20} />
              Analizando...
            </>
          ) : (
            <>
              <Send size={20} />
              Analizar Sentimiento
            </>
          )}
        </button>
      </div>

      {/* Error Message */}
      {error && (
        // Agregamos dark:bg-red-900/20 dark:border-red-800
        <div className="bg-red-50 border border-red-200 rounded-lg p-4 flex items-start gap-3 dark:bg-red-900/20 dark:border-red-800">
          <AlertCircle className="text-red-600 flex-shrink-0 mt-0.5 dark:text-red-400" size={20} />
          <div>
            <h3 className="text-red-800 font-medium dark:text-red-300">Error</h3>
            <p className="text-red-700 text-sm mt-1 dark:text-red-400">{error}</p>
          </div>
        </div>
      )}

      {/* Result Section */}
      {result && (
        // Agregamos dark:bg-slate-800 dark:border-slate-700
        <div className="bg-white rounded-xl shadow-lg p-6 border border-slate-200 animate-fade-in dark:bg-slate-800 dark:border-slate-700">
          <div className="flex items-center gap-3 mb-4">
            <CheckCircle2 className="text-green-600 dark:text-green-500" size={24} />
            {/* Agregamos dark:text-white */}
            <h2 className="text-xl font-semibold text-slate-800 dark:text-white">
              Resultado del Análisis
            </h2>
          </div>

          <div className="space-y-4">
            {/* Sentiment Badge */}
            <div className="flex items-center gap-4">
              <span className="text-4xl">{getSentimentIcon(result.prevision)}</span>
              <div
                className={`px-6 py-3 rounded-lg border-2 font-bold text-lg ${getSentimentColor(
                  result.prevision
                )}`}
              >
                {result.prevision}
              </div>
            </div>

            {/* Probability */}
            {/* Agregamos dark:bg-slate-900/50 */}
            <div className="bg-slate-50 rounded-lg p-4 dark:bg-slate-900/50">
              <div className="flex items-center justify-between mb-2">
                <span className="text-sm font-medium text-slate-600 dark:text-slate-400">
                  Nivel de Confianza
                </span>
                <span
                  className={`text-2xl font-bold ${getProbabilityColor(
                    result.probabilidad
                  )}`}
                >
                  {(result.probabilidad * 100).toFixed(1)}%
                </span>
              </div>
              {/* Agregamos dark:bg-slate-700 */}
              <div className="w-full bg-slate-200 rounded-full h-3 dark:bg-slate-700">
                <div
                  className={`h-3 rounded-full transition-all ${
                    result.probabilidad >= 0.7
                      ? 'bg-green-500'
                      : result.probabilidad >= 0.5
                      ? 'bg-yellow-500'
                      : 'bg-orange-500'
                  }`}
                  style={{ width: `${result.probabilidad * 100}%` }}
                />
              </div>
            </div>

            {/* Original Text */}
            {/* Agregamos dark:bg-slate-900/50 */}
            <div className="bg-slate-50 rounded-lg p-4 dark:bg-slate-900/50">
              <p className="text-sm font-medium text-slate-600 mb-2 dark:text-slate-400">
                Texto Analizado
              </p>
              {/* Agregamos dark:text-slate-300 */}
              <p className="text-slate-800 italic dark:text-slate-300">"{text}"</p>
            </div>
          </div>
        </div>
      )}

      {/* Example Texts */}
      {/* Agregamos dark:bg-slate-800 dark:border-slate-700 */}
      <div className="bg-white rounded-xl shadow-lg p-6 border border-slate-200 dark:bg-slate-800 dark:border-slate-700">
        <h3 className="text-lg font-semibold text-slate-800 mb-4 dark:text-white">
          Ejemplos para Probar
        </h3>
        <div className="grid grid-cols-1 md:grid-cols-3 gap-3">
          {[
            {
              text: 'Excelente servicio, muy recomendado',
              sentiment: 'Positivo',
            },
            {
              text: 'normal el producto',
              sentiment: 'Neutro',
            },
            {
              text: 'Muy mala calidad, no lo recomiendo',
              sentiment: 'Negativo',
            },
          ].map((example, idx) => (
            <button
              key={idx}
              onClick={() => setText(example.text)}
              // Agregamos dark:bg-slate-900 dark:border-slate-700 dark:hover:bg-slate-800
              className="text-left p-3 bg-slate-50 hover:bg-slate-100 rounded-lg border border-slate-200 transition-colors dark:bg-slate-900 dark:border-slate-700 dark:hover:bg-slate-800"
            >
              <p className="text-sm text-slate-700 italic mb-1 dark:text-slate-300">
                "{example.text}"
              </p>
              <p className="text-xs text-slate-500 dark:text-slate-500">Esperado: {example.sentiment}</p>
            </button>
          ))}
        </div>
      </div>
    </div>
  )
}

export default SentimentAnalyzer