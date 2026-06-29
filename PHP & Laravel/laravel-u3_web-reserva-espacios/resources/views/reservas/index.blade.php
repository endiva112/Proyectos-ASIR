<x-app-layout>
    <x-slot name="header">
        <div class="flex flex-col sm:flex-row sm:items-center sm:justify-between gap-4">
            <!-- Título -->
            <h2 class="font-semibold text-xl text-gray-800 dark:text-gray-200 leading-tight">
                Reserva de espacios
            </h2>

            <!-- Selector de aula -->
            <div class="flex items-center gap-2">
                <label for="aula" class="font-semibold text-gray-200">Selecciona aula:</label>
                <select id="aula" class="border rounded px-3 py-1"
                        onchange="window.location='?aula_id=' + this.value">
                    @foreach ($aulas as $aula)
                        <option value="{{ $aula->id }}" {{ $aula->id == ($selectedAulaId ?? $aulas->first()->id) ? 'selected' : '' }}>
                            {{ $aula->nombre }}
                        </option>
                    @endforeach
                </select>
            </div>
        </div>
    </x-slot>

    <div class="py-6">
        <div class="max-w-7xl mx-auto px-6">

            <!-- Navegación semana -->
            <div class="bg-white rounded-lg shadow p-4 mb-6 flex items-center justify-between">
                <a href="{{ route('reservas.index', ['start' => $start->copy()->subWeek()->format('Y-m-d'), 'aula_id' => $selectedAulaId]) }}"
                   class="px-3 py-1 bg-gray-200 rounded hover:bg-gray-300">←</a>
                <div class="text-center">
                    <p class="font-semibold">{{ $start->format('d M') }} – {{ $end->format('d M') }}</p>
                    <p class="text-sm text-gray-500">Semana {{ $start->weekOfYear }}, {{ $start->year }}</p>
                </div>
                <a href="{{ route('reservas.index', ['start' => $start->copy()->addWeek()->format('Y-m-d'), 'aula_id' => $selectedAulaId]) }}"
                   class="px-3 py-1 bg-gray-200 rounded hover:bg-gray-300">→</a>
            </div>

            <!-- Tablas mañana y tarde -->
            <div class="flex gap-6 overflow-x-auto">
                @foreach (['Mañana', 'Tarde'] as $turno)
                    <div class="flex-1 bg-white rounded-lg shadow p-4">
                        <h3 class="font-semibold mb-2 text-center">{{ $turno }}</h3>
                        <table class="w-full table-fixed border-collapse text-center">
                            <thead>
                                <tr class="bg-gray-100">
                                    <th class="border p-2"></th>
                                    @for ($d = 0; $d < 5; $d++)
                                        <th class="border p-2">{{ $start->copy()->addDays($d)->format('l') }}</th>
                                    @endfor
                                </tr>
                            </thead>
                            <tbody>
                                @foreach ($franjas as $franja)
                                    @if(($turno == 'Mañana' && $franja->turno == 'mañana') ||
                                        ($turno == 'Tarde' && $franja->turno == 'tarde'))
                                        <tr>
                                            <td class="border p-2 font-semibold">{{ $franja->nombre }}</td>

                                            @for ($d = 0; $d < 5; $d++)
                                                @php
                                                    $fecha = $start->copy()->addDays($d)->format('Y-m-d');
                                                    $reserva = $reservas[$franja->id][$fecha][$selectedAulaId] ?? null;
                                                @endphp
                                                
                                                @if($reserva)
                                                    {{-- Celda con reserva --}}
                                                    <td class="border p-2 {{ $reserva->user_id == auth()->id() ? 'bg-blue-100 hover:bg-blue-200 cursor-pointer' : 'bg-gray-100' }}"
                                                        @if($reserva->user_id == auth()->id())
                                                            onclick="window.location='{{ route('reservas.edit', $reserva->id) }}'"
                                                            title="Tu reserva - Click para editar"
                                                        @else
                                                            title="Reservado por {{ $reserva->user->name }}"
                                                        @endif>
                                                        {{ $reserva->user->name }}
                                                    </td>
                                                @else
                                                    {{-- Celda vacía - click para crear reserva --}}
                                                    <td class="border p-2 hover:bg-gray-100 cursor-pointer"
                                                        onclick="window.location='{{ route('reservas.create', [
                                                            'aula_id' => $selectedAulaId,
                                                            'franja_horaria_id' => $franja->id,
                                                            'fecha' => $fecha
                                                        ]) }}'"
                                                        title="Click para reservar">
                                                    </td>
                                                @endif
                                            @endfor
                                        </tr>
                                    @endif
                                @endforeach
                            </tbody>
                        </table>
                    </div>
                @endforeach
            </div>

            <!-- Mensajes -->
            <div class="mt-6 p-4 bg-blue-50 border border-blue-200 rounded text-blue-800">
                Selecciona una celda libre para crear una reserva.
            </div>

        </div>
    </div>
</x-app-layout>