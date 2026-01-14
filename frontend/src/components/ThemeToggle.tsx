import { useEffect, useState } from 'react';

export const ThemeToggle = () => {
const [theme, setTheme] = useState(() => {
    // Lee la memoria del navegador para ver si ya habías elegido modo oscuro antes
    if (localStorage.getItem('theme')) return localStorage.getItem('theme');
    return window.matchMedia('(prefers-color-scheme: dark)').matches ? 'dark' : 'light';
});

useEffect(() => {
    // Agrega o quita la clase "dark" al HTML real
    if (theme === 'dark') {
    document.documentElement.classList.add('dark');
    localStorage.setItem('theme', 'dark');
    } else {
    document.documentElement.classList.remove('dark');
    localStorage.setItem('theme', 'light');
    }
}, [theme]);

return (
    <button
    onClick={() => setTheme(t => t === 'light' ? 'dark' : 'light')}
    className="p-2 rounded-lg bg-gray-200 dark:bg-gray-700 text-gray-800 dark:text-white transition-colors hover:bg-gray-300 dark:hover:bg-gray-600"
    title="Cambiar tema"
    >
    {theme === 'dark' ? '🌙' : '☀️'}
    </button>
);
};