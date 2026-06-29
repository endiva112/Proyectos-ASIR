<?php

namespace App\Http\Controllers;

use App\Models\Aula;
use App\Models\Reserva;
use App\Models\FranjaHoraria;
use Illuminate\Http\Request;
use Carbon\Carbon;

class ReservaController extends Controller
{
    /**
     * Mostrar formulario de reservas y reservas existentes
     */
    public function index(Request $request)
    {
        $selectedAulaId = $request->query('aula_id', Aula::first()->id ?? 1);

        // Semana a mostrar
        $start = Carbon::parse($request->query('start', now()->startOfWeek())); // lunes
        $end = $start->copy()->addDays(4); // viernes

        $aulas = Aula::all();
        $franjas = FranjaHoraria::orderBy('orden')->get();

        // Cargar reservas para el rango y aula seleccionada
        $reservasCollection = Reserva::with('user')
            ->where('aula_id', $selectedAulaId)
            ->whereBetween('fecha', [$start->format('Y-m-d'), $end->format('Y-m-d')])
            ->get();

        // Estructurar las reservas: $reservas[$franja_id][$fecha][$aula_id]
        $reservas = [];
        foreach ($reservasCollection as $reserva) {
            $reservas[$reserva->franja_horaria_id][$reserva->fecha][$reserva->aula_id] = $reserva;
        }

        return view('reservas.index', compact('start', 'end', 'aulas', 'franjas', 'reservas', 'selectedAulaId'));
    }

    /**
     * Mostrar formulario para crear una nueva reserva
     */
    public function create(Request $request)
    {
        $aulaId = $request->query('aula_id');
        $franjaHorariaId = $request->query('franja_horaria_id');
        $fecha = $request->query('fecha');

        // Verificar que los parámetros existan
        if (!$aulaId || !$franjaHorariaId || !$fecha) {
            return redirect()->route('reservas.index')->with('error', 'Faltan parámetros para crear la reserva');
        }

        // Verificar que no exista ya una reserva
        $existente = Reserva::where('aula_id', $aulaId)
            ->where('franja_horaria_id', $franjaHorariaId)
            ->where('fecha', $fecha)
            ->first();

        if ($existente) {
            return redirect()->route('reservas.index')->with('error', 'Ya existe una reserva para esta franja horaria');
        }

        $aula = Aula::findOrFail($aulaId);
        $franja = FranjaHoraria::findOrFail($franjaHorariaId);
        $fechaCarbon = Carbon::parse($fecha);

        return view('reservas.create', compact('aula', 'franja', 'fecha', 'fechaCarbon'));
    }

    /**
     * Crear una nueva reserva
     */
    public function store(Request $request)
    {
        $request->validate([
            'aula_id'            => 'required|exists:aulas,id',
            'franja_horaria_id'  => 'required|exists:franjas_horarias,id',
            'fecha'              => 'required|date',
            'grupo'              => 'required|string|max:255',
            'motivo'             => 'required|string|max:500',
        ]);

        // Verificar si ya existe una reserva para esa franja, fecha y aula
        $existente = Reserva::where('aula_id', $request->aula_id)
            ->where('franja_horaria_id', $request->franja_horaria_id)
            ->where('fecha', $request->fecha)
            ->first();

        if ($existente) {
            return redirect()->back()->with('error', 'Ya existe una reserva para esta franja horaria y aula');
        }

        Reserva::create([
            'user_id'            => auth()->id(),
            'aula_id'            => $request->aula_id,
            'franja_horaria_id'  => $request->franja_horaria_id,
            'fecha'              => $request->fecha,
            'grupo'              => $request->grupo,
            'motivo'             => $request->motivo,
        ]);

        // Calcular el lunes de la semana de la fecha reservada
        $fechaReserva = Carbon::parse($request->fecha);
        $lunesReserva = $fechaReserva->copy()->startOfWeek();

        return redirect()->route('reservas.index', [
            'aula_id' => $request->aula_id,
            'start' => $lunesReserva->format('Y-m-d')
        ])->with('success', 'Reserva creada correctamente');
    }

    /**
     * Mostrar formulario para editar una reserva
     */
    public function edit(Reserva $reserva)
    {
        // Verificar que la reserva pertenece al usuario actual
        if ($reserva->user_id !== auth()->id()) {
            return redirect()->route('reservas.index')->with('error', 'No tienes permiso para editar esta reserva');
        }

        $aula = $reserva->aula;
        $franja = $reserva->franjaHoraria;
        $fechaCarbon = Carbon::parse($reserva->fecha);

        return view('reservas.edit', compact('reserva', 'aula', 'franja', 'fechaCarbon'));
    }

    /**
     * Actualizar una reserva existente
     */
    public function update(Request $request, Reserva $reserva)
    {
        // Verificar que la reserva pertenece al usuario actual
        if ($reserva->user_id !== auth()->id()) {
            return redirect()->route('reservas.index')->with('error', 'No tienes permiso para editar esta reserva');
        }

        $request->validate([
            'grupo'  => 'required|string|max:255',
            'motivo' => 'required|string|max:500',
        ]);

        $reserva->update([
            'grupo'  => $request->grupo,
            'motivo' => $request->motivo,
        ]);

        // Calcular el lunes de la semana de la fecha reservada
        $fechaReserva = Carbon::parse($reserva->fecha);
        $lunesReserva = $fechaReserva->copy()->startOfWeek();

        return redirect()->route('reservas.index', [
            'aula_id' => $reserva->aula_id,
            'start' => $lunesReserva->format('Y-m-d')
        ])->with('success', 'Reserva actualizada correctamente');
    }

    /**
     * Eliminar una reserva
     */
    public function destroy(Reserva $reserva)
    {
        // Verificar que la reserva pertenece al usuario actual
        if ($reserva->user_id !== auth()->id()) {
            return redirect()->route('reservas.index')->with('error', 'No tienes permiso para eliminar esta reserva');
        }

        $aulaId = $reserva->aula_id;
        
        // Calcular el lunes de la semana de la fecha reservada ANTES de eliminar
        $fechaReserva = Carbon::parse($reserva->fecha);
        $lunesReserva = $fechaReserva->copy()->startOfWeek();
        
        $reserva->delete();

        return redirect()->route('reservas.index', [
            'aula_id' => $aulaId,
            'start' => $lunesReserva->format('Y-m-d')
        ])->with('success', 'Reserva eliminada correctamente');
    }
}