import { InfluentialWord } from '../services/api'
import { TrendingUp, TrendingDown, Minus } from 'lucide-react'

interface InfluentialWordsProps {
  words: InfluentialWord[]
  explanation: string
}

const InfluentialWords = ({ words, explanation }: InfluentialWordsProps) => {
  const getWordIcon = (sentiment: string) => {
    switch (sentiment) {
      case 'positivo':
        return <TrendingUp size={16} className="text-green-600" />
      case 'negativo':
        return <TrendingDown size={16} className="text-red-600" />
      default:
        return <Minus size={16} className="text-gray-600" />
    }
  }

  const getWordColor = (sentiment: string) => {
    switch (sentiment) {
      case 'positivo':
        return 'bg-green-100 text-green-800 border-green-300 dark:bg-green-900/30 dark:text-green-400 dark:border-green-800'
      case 'negativo':
        return 'bg-red-100 text-red-800 border-red-300 dark:bg-red-900/30 dark:text-red-400 dark:border-red-800'
      default:
        return 'bg-gray-100 text-gray-800 border-gray-300 dark:bg-slate-700 dark:text-slate-300 dark:border-slate-600'
    }
  }

  const getImportanceColor = (importance: number) => {
    if (importance > 0) return 'text-green-600 dark:text-green-400'
    if (importance < 0) return 'text-red-600 dark:text-red-400'
    return 'text-gray-600 dark:text-gray-400'
  }

  if (!words || words.length === 0) {
    return null
  }

  return (
    <div className="bg-slate-50 rounded-lg p-4 dark:bg-slate-900/50">
      <h4 className="text-sm font-medium text-slate-600 mb-3 dark:text-slate-400">
        Palabras Influyentes
      </h4>
      
      {/* Explanation */}
      {explanation && (
        <div className="mb-4 p-3 bg-blue-50 rounded-lg border border-blue-200 dark:bg-blue-900/20 dark:border-blue-800">
          <p className="text-sm text-blue-800 dark:text-blue-300">
            💡 {explanation}
          </p>
        </div>
      )}

      {/* Words Grid */}
      <div className="grid grid-cols-1 sm:grid-cols-2 gap-2">
        {words.map((word, index) => (
          <div
            key={index}
            className={`flex items-center gap-2 p-2 rounded-lg border ${getWordColor(
              word.sentiment
            )}`}
          >
            {getWordIcon(word.sentiment)}
            <span className="font-medium text-sm">{word.word}</span>
            <span className={`text-xs font-bold ${getImportanceColor(word.importance)}`}>
              ({word.importance > 0 ? '+' : ''}{word.importance})
            </span>
          </div>
        ))}
      </div>

      <div className="mt-3 text-xs text-slate-500 dark:text-slate-400">
        <p>Los números muestran el impacto de cada palabra en la predicción.</p>
        <p>Valores positivos influyen hacia el sentimiento detectado, negativos en contra.</p>
      </div>
    </div>
  )
}

export default InfluentialWords
