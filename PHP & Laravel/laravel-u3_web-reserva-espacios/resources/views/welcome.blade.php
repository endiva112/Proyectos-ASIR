<!DOCTYPE html>
<html lang="{{ str_replace('_', '-', app()->getLocale()) }}">
    <head>
        <meta charset="utf-8">
        <meta name="viewport" content="width=device-width, initial-scale=1">

        <title>{{ config('app.name', 'Laravel') }}</title>

        <!-- Fonts -->
        <link rel="preconnect" href="https://fonts.bunny.net">
        <link href="https://fonts.bunny.net/css?family=instrument-sans:400,500,600" rel="stylesheet" />

        <!-- Scripts -->
        @vite(['resources/css/app.css', 'resources/js/app.js'])
    </head>
    <body class="bg-gradient-to-br from-blue-50 to-indigo-100 dark:from-gray-900 dark:to-gray-800 text-gray-800 dark:text-gray-200 h-screen flex flex-col overflow-hidden">
        
        <!-- Header -->
        <header class="w-full px-6 py-4">
            @if (Route::has('login'))
                <nav class="flex items-center justify-end gap-4 max-w-7xl mx-auto">
                    @auth
                        <a href="{{ url('/dashboard') }}" class="inline-block px-5 py-2 bg-blue-600 hover:bg-blue-700 text-white rounded-lg shadow-md transition-all font-medium text-sm">
                            Dashboard
                        </a>
                    @else
                        <a href="{{ route('login') }}" class="inline-block px-4 py-2 text-gray-700 dark:text-gray-200 hover:text-blue-600 dark:hover:text-blue-400 transition-colors font-medium text-sm">
                            Iniciar sesión
                        </a>

                        @if (Route::has('register'))
                            <a href="{{ route('register') }}" class="inline-block px-5 py-2 bg-blue-600 hover:bg-blue-700 text-white rounded-lg shadow-md transition-all font-medium text-sm">
                                Registrarse
                            </a>
                        @endif
                    @endauth
                </nav>
            @endif
        </header>

        <!-- Main Content -->
        <main class="flex-1 flex items-center justify-center px-6 pb-6">
            <div class="flex max-w-6xl w-full flex-col lg:flex-row gap-6 items-stretch">
                
                <!-- Sección de información -->
                <div class="flex-1 bg-white dark:bg-gray-800 rounded-2xl shadow-2xl p-6 lg:p-8 flex flex-col justify-between">
                    <div>
                        <h1 class="text-3xl lg:text-4xl font-bold mb-3 text-blue-600 dark:text-blue-400">
                            Sistema de Reserva de Espacios
                        </h1>
                        <p class="text-base text-gray-600 dark:text-gray-300 mb-6">
                            Gestiona las reservas de aulas y espacios educativos de forma simple y eficiente
                        </p>

                        <div class="space-y-4">
                            <div class="flex items-start gap-3">
                                <div class="flex-shrink-0 w-10 h-10 bg-blue-100 dark:bg-blue-900 rounded-lg flex items-center justify-center">
                                    <svg class="w-5 h-5 text-blue-600 dark:text-blue-400" fill="none" stroke="currentColor" viewBox="0 0 24 24">
                                        <path stroke-linecap="round" stroke-linejoin="round" stroke-width="2" d="M8 7V3m8 4V3m-9 8h10M5 21h14a2 2 0 002-2V7a2 2 0 00-2-2H5a2 2 0 00-2 2v12a2 2 0 002 2z"></path>
                                    </svg>
                                </div>
                                <div>
                                    <h3 class="font-semibold mb-1">Visualización por semanas</h3>
                                    <p class="text-sm text-gray-600 dark:text-gray-400">
                                        Consulta la disponibilidad de aulas por franjas horarias
                                    </p>
                                </div>
                            </div>

                            <div class="flex items-start gap-3">
                                <div class="flex-shrink-0 w-10 h-10 bg-green-100 dark:bg-green-900 rounded-lg flex items-center justify-center">
                                    <svg class="w-5 h-5 text-green-600 dark:text-green-400" fill="none" stroke="currentColor" viewBox="0 0 24 24">
                                        <path stroke-linecap="round" stroke-linejoin="round" stroke-width="2" d="M9 5H7a2 2 0 00-2 2v12a2 2 0 002 2h10a2 2 0 002-2V7a2 2 0 00-2-2h-2M9 5a2 2 0 002 2h2a2 2 0 002-2M9 5a2 2 0 012-2h2a2 2 0 012 2m-6 9l2 2 4-4"></path>
                                    </svg>
                                </div>
                                <div>
                                    <h3 class="font-semibold mb-1">Reservas rápidas</h3>
                                    <p class="text-sm text-gray-600 dark:text-gray-400">
                                        Reserva con un clic indicando grupo y motivo
                                    </p>
                                </div>
                            </div>

                            <div class="flex items-start gap-3">
                                <div class="flex-shrink-0 w-10 h-10 bg-purple-100 dark:bg-purple-900 rounded-lg flex items-center justify-center">
                                    <svg class="w-5 h-5 text-purple-600 dark:text-purple-400" fill="none" stroke="currentColor" viewBox="0 0 24 24">
                                        <path stroke-linecap="round" stroke-linejoin="round" stroke-width="2" d="M11 5H6a2 2 0 00-2 2v11a2 2 0 002 2h11a2 2 0 002-2v-5m-1.414-9.414a2 2 0 112.828 2.828L11.828 15H9v-2.828l8.586-8.586z"></path>
                                    </svg>
                                </div>
                                <div>
                                    <h3 class="font-semibold mb-1">Gestión personalizada</h3>
                                    <p class="text-sm text-gray-600 dark:text-gray-400">
                                        Edita o cancela tus propias reservas
                                    </p>
                                </div>
                            </div>
                        </div>
                    </div>

                    <div class="mt-6 pt-6 border-t border-gray-200 dark:border-gray-700">
                        @auth
                            <a href="{{ route('reservas.index') }}" class="inline-block w-full text-center px-6 py-3 bg-blue-600 hover:bg-blue-700 text-white rounded-lg shadow-lg transition-all font-semibold">
                                Ver calendario de reservas
                            </a>
                        @else
                            <div class="text-center">
                                <p class="text-gray-600 dark:text-gray-400 mb-3 text-sm">
                                    Inicia sesión para gestionar tus reservas
                                </p>
                                <a href="{{ route('login') }}" class="inline-block px-6 py-3 bg-blue-600 hover:bg-blue-700 text-white rounded-lg shadow-lg transition-all font-semibold">
                                    Acceder al sistema
                                </a>
                            </div>
                        @endauth
                    </div>
                </div>

                <!-- Sección visual/ilustración -->
                <div class="lg:w-80 bg-gradient-to-br from-blue-500 to-indigo-600 rounded-2xl shadow-2xl p-8 flex items-center justify-center">
                    <div class="text-center text-white">
                        <svg class="w-32 h-32 lg:w-40 lg:h-40 mx-auto mb-4" fill="none" stroke="currentColor" viewBox="0 0 24 24">
                            <path stroke-linecap="round" stroke-linejoin="round" stroke-width="1.5" d="M19 21V5a2 2 0 00-2-2H7a2 2 0 00-2 2v16m14 0h2m-2 0h-5m-9 0H3m2 0h5M9 7h1m-1 4h1m4-4h1m-1 4h1m-5 10v-5a1 1 0 011-1h2a1 1 0 011 1v5m-4 0h4"></path>
                        </svg>
                        <h2 class="text-xl lg:text-2xl font-bold mb-2">Optimiza el uso de espacios</h2>
                        <p class="text-sm text-blue-100">
                            Gestión eficiente de instalaciones educativas
                        </p>
                    </div>
                </div>

            </div>
        </main>

        <!-- Footer -->
        <footer class="py-3 text-center text-xs text-gray-500 dark:text-gray-400">
            <p>Sistema de Gestión de Reservas Educativas</p>
        </footer>

    </body>
</html>