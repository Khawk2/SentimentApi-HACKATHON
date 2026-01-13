# Frontend - Sentiment Analysis Dashboard

Frontend moderno para la API de Análisis de Sentimientos construido con React, TypeScript, Vite y Tailwind CSS.

## Características

- **Análisis en Tiempo Real**: Analiza textos en español y obtén resultados instantáneos
- **Dashboard de Estadísticas**: Visualiza métricas y gráficos de los análisis realizados
- **Diseño Moderno**: Interfaz responsive y atractiva con Tailwind CSS
- **Visualizaciones**: Gráficos de barras y pastel usando Recharts

## Tecnologías

- **React 18**: Biblioteca de UI
- **TypeScript**: Tipado estático
- **Vite**: Build tool rápido
- **Tailwind CSS**: Framework de estilos
- **Axios**: Cliente HTTP
- **Recharts**: Librería de gráficos
- **Lucide React**: Iconos modernos

## Instalación

### Desarrollo Local

1. Instala las dependencias:
```bash
npm install
```

2. Inicia el servidor de desarrollo:
```bash
npm run dev
```

3. Abre tu navegador en `http://localhost:3000`

### Con Docker

El frontend está incluido en el `docker-compose.yml` del proyecto principal. Para ejecutarlo:

```bash
docker-compose up --build
```

El frontend estará disponible en `http://localhost:3000`

## Estructura del Proyecto

```
frontend/
├── src/
│   ├── components/
│   │   ├── SentimentAnalyzer.tsx    # Componente de análisis
│   │   └── StatsDashboard.tsx        # Dashboard de estadísticas
│   ├── services/
│   │   └── api.ts                    # Cliente API
│   ├── App.tsx                       # Componente principal
│   ├── main.tsx                      # Punto de entrada
│   └── index.css                     # Estilos globales
├── public/                           # Archivos estáticos
├── Dockerfile                        # Configuración Docker
├── nginx.conf                        # Configuración Nginx
└── package.json                      # Dependencias
```

## Configuración de la API

Por defecto, el frontend se conecta a `http://localhost:8080`. Puedes cambiar esto creando un archivo `.env`:

```env
VITE_API_URL=http://localhost:8080
```

## Scripts Disponibles

- `npm run dev`: Inicia el servidor de desarrollo
- `npm run build`: Construye la aplicación para producción
- `npm run preview`: Previsualiza la build de producción
- `npm run lint`: Ejecuta el linter

## Funcionalidades

### Pestaña "Analizar Texto"
- Campo de texto para ingresar comentarios
- Botón de análisis con estado de carga
- Visualización de resultados con:
  - Sentimiento (Positivo/Negativo/Neutro)
  - Nivel de confianza (probabilidad)
  - Barra de progreso visual
- Ejemplos predefinidos para probar

### Pestaña "Estadísticas"
- Tarjetas con resumen de totales
- Gráfico de pastel con distribución
- Gráfico de barras comparativo
- Actualización automática cada 10 segundos

## Docker

El Dockerfile usa una estrategia multi-stage:
1. **Build stage**: Compila la aplicación React
2. **Production stage**: Sirve la aplicación con Nginx

Nginx está configurado para:
- Servir la aplicación SPA
- Proxificar peticiones `/api` al backend
- Comprimir respuestas con gzip
- Cachear assets estáticos

## Responsive Design

El frontend está completamente optimizado para:
- Móviles
- Tablets
- Desktop

## CORS

El backend ya tiene configurado CORS para permitir peticiones desde el frontend. Si necesitas cambiar los orígenes permitidos, modifica `SentimentController.java` en el backend.

## Licencia

Proyecto desarrollado para el **Hackathon One** - Clasificación de Sentimientos

## Equipo

- **Data Science:** Modelo y análisis de datos
- **Backend:** API REST e integración de servicios
- **Frontend:** Interfaz de usuario y experiencia