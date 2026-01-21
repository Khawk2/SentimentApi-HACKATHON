import React from 'react'

interface SentimentLogoProps {
  size?: number
  className?: string
}

export const SentimentLogo: React.FC<SentimentLogoProps> = ({ 
  size = 32, 
  className = "" 
}) => {
  return (
    <svg 
      width={size} 
      height={size} 
      viewBox="0 0 120 120" 
      className={className}
      xmlns="http://www.w3.org/2000/svg"
    >
      {/* Fondo circular azul grisáceo */}
      <circle cx="60" cy="60" r="58" fill="#64748b" />
      
      {/* Barra roja - sentimiento negativo */}
      <rect x="25" y="70" width="20" height="25" fill="#dc2626" rx="2">
        <animate attributeName="height" values="0;25" dur="0.5s" begin="0s" fill="freeze"/>
        <animate attributeName="y" values="95;70" dur="0.5s" begin="0s" fill="freeze"/>
      </rect>
      
      {/* Barra naranja - sentimiento neutral */}
      <rect x="50" y="55" width="20" height="40" fill="#ea580c" rx="2">
        <animate attributeName="height" values="0;40" dur="0.5s" begin="0.2s" fill="freeze"/>
        <animate attributeName="y" values="95;55" dur="0.5s" begin="0.2s" fill="freeze"/>
      </rect>
      
      {/* Barra verde - sentimiento positivo */}
      <rect x="75" y="40" width="20" height="55" fill="#16a34a" rx="2">
        <animate attributeName="height" values="0;55" dur="0.5s" begin="0.4s" fill="freeze"/>
        <animate attributeName="y" values="95;40" dur="0.5s" begin="0.4s" fill="freeze"/>
      </rect>
      
      {/* Carita triste - barra roja */}
      <g transform="translate(35, 55)">
        <circle cx="0" cy="0" r="8" fill="white" opacity="0.9">
          <animate attributeName="opacity" values="0;0.9" dur="0.3s" begin="0.6s" fill="freeze"/>
        </circle>
        {/* Ojos tristes */}
        <circle cx="-3" cy="-2" r="1.5" fill="#dc2626">
          <animate attributeName="opacity" values="0;1" dur="0.2s" begin="0.8s" fill="freeze"/>
        </circle>
        <circle cx="3" cy="-2" r="1.5" fill="#dc2626">
          <animate attributeName="opacity" values="0;1" dur="0.2s" begin="0.8s" fill="freeze"/>
        </circle>
        {/* Boca triste */}
        <path d="M -4,2 Q 0,0 4,2" stroke="#dc2626" strokeWidth="1.5" fill="none" strokeLinecap="round">
          <animate attributeName="opacity" values="0;1" dur="0.2s" begin="1s" fill="freeze"/>
        </path>
      </g>
      
      {/* Carita neutral - barra naranja */}
      <g transform="translate(60, 40)">
        <circle cx="0" cy="0" r="8" fill="white" opacity="0.9">
          <animate attributeName="opacity" values="0;0.9" dur="0.3s" begin="0.7s" fill="freeze"/>
        </circle>
        {/* Ojos neutrales */}
        <circle cx="-3" cy="-2" r="1.5" fill="#ea580c">
          <animate attributeName="opacity" values="0;1" dur="0.2s" begin="0.9s" fill="freeze"/>
        </circle>
        <circle cx="3" cy="-2" r="1.5" fill="#ea580c">
          <animate attributeName="opacity" values="0;1" dur="0.2s" begin="0.9s" fill="freeze"/>
        </circle>
        {/* Boca neutral */}
        <line x1="-3" y1="3" x2="3" y2="3" stroke="#ea580c" strokeWidth="1.5" strokeLinecap="round">
          <animate attributeName="opacity" values="0;1" dur="0.2s" begin="1.1s" fill="freeze"/>
        </line>
      </g>
      
      {/* Carita feliz - barra verde */}
      <g transform="translate(85, 25)">
        <circle cx="0" cy="0" r="8" fill="white" opacity="0.9">
          <animate attributeName="opacity" values="0;0.9" dur="0.3s" begin="0.8s" fill="freeze"/>
        </circle>
        {/* Ojos felices */}
        <circle cx="-3" cy="-2" r="1.5" fill="#16a34a">
          <animate attributeName="opacity" values="0;1" dur="0.2s" begin="1s" fill="freeze"/>
        </circle>
        <circle cx="3" cy="-2" r="1.5" fill="#16a34a">
          <animate attributeName="opacity" values="0;1" dur="0.2s" begin="1s" fill="freeze"/>
        </circle>
        {/* Boca feliz */}
        <path d="M -4,1 Q 0,4 4,1" stroke="#16a34a" strokeWidth="1.5" fill="none" strokeLinecap="round">
          <animate attributeName="opacity" values="0;1" dur="0.2s" begin="1.2s" fill="freeze"/>
        </path>
      </g>
    </svg>
  )
}

export default SentimentLogo
