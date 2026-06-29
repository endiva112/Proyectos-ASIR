<x-app-layout>
    <x-slot name="header">
        <h2 class="font-semibold text-xl text-gray-800 dark:text-gray-200 leading-tight">
            Panel principal
        </h2>
    </x-slot>

    <div class="py-12">
        <div class="max-w-5xl mx-auto sm:px-6 lg:px-8">
            <div class="bg-white dark:bg-gray-800 overflow-hidden shadow-sm sm:rounded-lg">
                <div class="p-10 text-center">

                    <h1 class="text-3xl font-bold text-gray-800 dark:text-gray-100 mb-4">
                        Reserva de espacios del centro
                    </h1>

                    <p class="text-gray-600 dark:text-gray-300 mb-8">
                        Aplicación para que el profesorado reserve aulas, laboratorios
                        y otros espacios en una fecha y franja horaria concreta.
                    </p>

                    <a href="{{ route('reservas.index') }}"
                       class="inline-flex items-center px-6 py-3 bg-indigo-600 hover:bg-indigo-700 text-white font-semibold rounded-lg shadow transition">
                        Reservar un aula
                    </a>

                </div>
            </div>
        </div>
    </div>
</x-app-layout>
