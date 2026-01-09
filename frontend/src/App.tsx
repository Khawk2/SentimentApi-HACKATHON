import { useState } from 'react'
import SentimentAnalyzer from './components/SentimentAnalyzer'
import StatsDashboard from './components/StatsDashboard'
import { BarChart3, MessageSquare } from 'lucide-react'

function App() {
  const [activeTab, setActiveTab] = useState<'analyze' | 'stats'>('analyze')

  return (
    <div className="min-h-screen bg-gradient-to-br from-slate-50 to-slate-100">
      {/* Header */}
      <header className="bg-white shadow-sm border-b border-slate-200">
        <div className="max-w-7xl mx-auto px-4 sm:px-6 lg:px-8 py-4">
          <h1 className="text-3xl font-bold text-slate-800">
            📊 Análisis de Sentimientos
          </h1>
          <p className="text-slate-600 mt-1">
            Clasifica textos en español como Positivo, Neutro o Negativo
          </p>
        </div>
      </header>

      {/* Navigation Tabs */}
      <div className="max-w-7xl mx-auto px-4 sm:px-6 lg:px-8 mt-6">
        <div className="flex space-x-1 bg-white p-1 rounded-lg shadow-sm border border-slate-200">
          <button
            onClick={() => setActiveTab('analyze')}
            className={`flex-1 flex items-center justify-center gap-2 px-4 py-3 rounded-md font-medium transition-all ${
              activeTab === 'analyze'
                ? 'bg-blue-600 text-white shadow-md'
                : 'text-slate-600 hover:bg-slate-100'
            }`}
          >
            <MessageSquare size={20} />
            Analizar Texto
          </button>
          <button
            onClick={() => setActiveTab('stats')}
            className={`flex-1 flex items-center justify-center gap-2 px-4 py-3 rounded-md font-medium transition-all ${
              activeTab === 'stats'
                ? 'bg-blue-600 text-white shadow-md'
                : 'text-slate-600 hover:bg-slate-100'
            }`}
          >
            <BarChart3 size={20} />
            Estadísticas
          </button>
        </div>
      </div>

      {/* Main Content */}
      <main className="max-w-7xl mx-auto px-4 sm:px-6 lg:px-8 py-8">
        {activeTab === 'analyze' ? <SentimentAnalyzer /> : <StatsDashboard />}
      </main>

      {/* Footer */}
      <footer className="mt-12 py-6 border-t border-slate-200 bg-white">
        <div className="max-w-7xl mx-auto px-4 sm:px-6 lg:px-8 text-center text-slate-600">
          <p>Sentiment Analysis API - Hackathon Project</p>
        </div>
      </footer>
    </div>
  )
}

export default App

