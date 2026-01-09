# Guía del Frontend - Sentiment Analysis Dashboard

## Inicio Rápido

### Opción 1: Desarrollo Local (Recomendado para desarrollo)

1. **Instala las dependencias:**
```bash
cd frontend
npm install
```

2. **Inicia el servidor de desarrollo:**
```bash
npm run dev
```

3. **Abre tu navegador en:** `http://localhost:3000`

**Nota:** Asegúrate de que el backend esté corriendo en `http://localhost:8080`

### Opción 2: Con Docker (Recomendado para producción)

El frontend está incluido en el `docker-compose.yml` principal. Para ejecutarlo:

```bash
# Desde la raíz del proyecto
docker-compose up --build
```

El frontend estará disponible en `http://localhost:3000`

## Características

### 1. Pestaña "Analizar Texto"

- **Campo de texto**: Ingresa cualquier texto en español para analizar
- **Análisis instantáneo**: Obtén resultados con sentimiento y probabilidad
- **Visualización clara**: 
  - Emoji según el sentimiento (😊 Positivo, 😞 Negativo, 😐 Neutro)
  - Badge de color según el resultado
  - Barra de progreso para el nivel de confianza
- **Ejemplos predefinidos**: Haz clic en los ejemplos para probar rápidamente
- **Atajos de teclado**: Ctrl + Enter para analizar

### 2. Pestaña "Estadísticas"

- **Tarjetas de resumen**: Total, Positivos, Negativos, Neutros
- **Gráfico de pastel**: Distribución visual de sentimientos
- **Gráfico de barras**: Comparación de cantidades
- **Actualización automática**: Se actualiza cada 10 segundos
- **Botón de actualización manual**: Para refrescar los datos cuando quieras

## Diseño

- **Responsive**: Funciona perfectamente en móviles, tablets y desktop
- **Colores temáticos**:
  - Verde para sentimientos positivos
  - Rojo para sentimientos negativos
  - Gris para sentimientos neutros
- **Animaciones suaves**: Transiciones y efectos visuales modernos
- **Iconos**: Lucide React para iconos consistentes y modernos

## Configuración

### Variables de Entorno

Crea un archivo `.env` en la carpeta `frontend`:

```env
VITE_API_URL=http://localhost:8080
```

Por defecto, si no especificas esta variable, usará `http://localhost:8080`

### Proxy en Desarrollo

El `vite.config.ts` está configurado para hacer proxy de las peticiones `/api` al backend durante el desarrollo. Esto evita problemas de CORS.

## Scripts Disponibles

- `npm run dev`: Inicia el servidor de desarrollo con hot-reload
- `npm run build`: Construye la aplicación para producción
- `npm run preview`: Previsualiza la build de producción localmente
- `npm run lint`: Ejecuta el linter para verificar el código

## Solución de Problemas

### El frontend no se conecta al backend

1. Verifica que el backend esté corriendo en el puerto 8080
2. Revisa la consola del navegador para ver errores de CORS
3. Asegúrate de que la variable `VITE_API_URL` esté correctamente configurada

### Error: "Cannot find module"

Ejecuta `npm install` para instalar todas las dependencias.

### Los gráficos no se muestran

Asegúrate de que `recharts` esté instalado:
```bash
npm install recharts
```

### Problemas con Docker

Si el frontend no carga en Docker:
1. Verifica los logs: `docker logs sentiment-frontend`
2. Asegúrate de que el backend esté corriendo antes del frontend
3. Revisa que el puerto 3000 no esté ocupado

## Próximas Mejoras

- [ ] Historial de análisis recientes
- [ ] Exportar estadísticas a CSV/PDF
- [ ] Modo oscuro
- [ ] Filtros por fecha en estadísticas
- [ ] Análisis de múltiples textos a la vez
- [ ] Comparación de textos

## Recursos

- [React Documentation](https://react.dev/)
- [Vite Documentation](https://vitejs.dev/)
- [Tailwind CSS](https://tailwindcss.com/)
- [Recharts](https://recharts.org/)

