<x-app-layout>
    <x-slot name="header">
        <h2 class="font-semibold text-xl text-gray-800 dark:text-gray-200 leading-tight">
            Nueva Reserva
        </h2>
    </x-slot>

    <div class="py-12">
        <div class="max-w-3xl mx-auto sm:px-6 lg:px-8">
            <div class="bg-white dark:bg-gray-800 overflow-hidden shadow-sm sm:rounded-lg">
                <div class="p-6 text-gray-900 dark:text-gray-100">
                    
                    <!-- Información de la reserva -->
                    <div class="mb-6 p-4 bg-blue-50 dark:bg-blue-900 rounded-lg">
                        <h3 class="font-semibold text-lg mb-3 text-blue-900 dark:text-blue-100">
                            Detalles de la reserva
                        </h3>
                        <div class="grid grid-cols-1 md:grid-cols-2 gap-3 text-sm">
                            <div>
                                <span class="font-semibold">Aula:</span>
                                <span class="ml-2">{{ $aula->nombre }}</span>
                            </div>
                            <div>
                                <span class="font-semibold">Fecha:</span>
                                <span class="ml-2">{{ $fechaCarbon->format('d/m/Y') }} ({{ $fechaCarbon->locale('es')->dayName }})</span>
                            </div>
                            <div>
                                <span class="font-semibold">Franja horaria:</span>
                                <span class="ml-2">{{ $franja->nombre }} ({{ ucfirst($franja->turno) }})</span>
                            </div>
                            <div>
                                <span class="font-semibold">Profesor:</span>
                                <span class="ml-2">{{ auth()->user()->name }}</span>
                            </div>
                        </div>
                    </div>

                    <!-- Formulario -->
                    <form method="POST" action="{{ route('reservas.store') }}">
                        @csrf

                        <!-- Campos ocultos -->
                        <input type="hidden" name="aula_id" value="{{ $aula->id }}">
                        <input type="hidden" name="franja_horaria_id" value="{{ $franja->id }}">
                        <input type="hidden" name="fecha" value="{{ $fecha }}">

                        <!-- Grupo -->
                        <div class="mb-4">
                            <label for="grupo" class="block font-medium text-sm text-gray-700 dark:text-gray-300 mb-2">
                                Grupo <span class="text-red-500">*</span>
                            </label>
                            <input 
                                type="text" 
                                id="grupo" 
                                name="grupo" 
                                value="{{ old('grupo') }}"
                                class="border-gray-300 dark:border-gray-700 dark:bg-gray-900 dark:text-gray-300 focus:border-indigo-500 dark:focus:border-indigo-600 focus:ring-indigo-500 dark:focus:ring-indigo-600 rounded-md shadow-sm w-full"
                                placeholder="Ej: 1º ESO A, 2º Bachillerato..."
                                required
                                autofocus>
                            @error('grupo')
                                <p class="text-red-500 text-sm mt-1">{{ $message }}</p>
                            @enderror
                        </div>

                        <!-- Motivo -->
                        <div class="mb-6">
                            <label for="motivo" class="block font-medium text-sm text-gray-700 dark:text-gray-300 mb-2">
                                Motivo <span class="text-red-500">*</span>
                            </label>
                            <textarea 
                                id="motivo" 
                                name="motivo" 
                                rows="4"
                                class="border-gray-300 dark:border-gray-700 dark:bg-gray-900 dark:text-gray-300 focus:border-indigo-500 dark:focus:border-indigo-600 focus:ring-indigo-500 dark:focus:ring-indigo-600 rounded-md shadow-sm w-full"
                                placeholder="Describe brevemente la actividad a realizar..."
                                required>{{ old('motivo') }}</textarea>
                            @error('motivo')
                                <p class="text-red-500 text-sm mt-1">{{ $message }}</p>
                            @enderror
                        </div>

                        <!-- Botones -->
                        <div class="flex items-center justify-end gap-3">
                            <a href="{{ route('reservas.index', ['aula_id' => $aula->id]) }}" 
                               class="px-4 py-2 bg-gray-300 dark:bg-gray-700 text-gray-700 dark:text-gray-300 rounded-md hover:bg-gray-400 dark:hover:bg-gray-600">
                                Cancelar
                            </a>
                            <button type="submit" 
                                    class="px-4 py-2 bg-blue-600 text-white rounded-md hover:bg-blue-700">
                                Crear Reserva
                            </button>
                        </div>
                    </form>

                </div>
            </div>
        </div>
    </div>
</x-app-layout>