import axios from 'axios'

// Para desarrollo: usar URL absoluta (o proxy de Vite)
// Para producción en Docker: usar ruta relativa '/api' (nginx hace proxy)
const API_BASE_URL = import.meta.env.VITE_API_URL || '/api'

const api = axios.create({
  baseURL: API_BASE_URL,
  headers: {
    'Content-Type': 'application/json',
  },
})

export interface SentimentRequest {
  text: string
}

export interface SentimentResponse {
  prevision: 'Positivo' | 'Negativo' | 'Neutro'
  probabilidad: number
}

export interface StatsResponse {
  total: number
  positivos: number
  negativos: number
  neutros: number
  porcentaje_positivos: number
  porcentaje_negativos: number
  porcentaje_neutros: number
}

export const sentimentApi = {
  analyzeSentiment: async (text: string): Promise<SentimentResponse> => {
    const response = await api.post<SentimentResponse>('/sentiment', {
      text,
    })
    return response.data
  },

  getStats: async (): Promise<StatsResponse> => {
    const response = await api.get<StatsResponse>('/stats')
    return response.data
  },

  healthCheck: async (): Promise<{ status: string; service: string }> => {
    const response = await api.get('/health')
    return response.data
  },
}

export default api

