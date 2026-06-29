<?php

namespace App\Models;

use Illuminate\Database\Eloquent\Model;

class FranjaHoraria extends Model
{
    protected $table = 'franjas_horarias';

    protected $fillable = [
        'nombre',
        'orden',
        'hora_inicio',
        'hora_fin',
        'turno',
    ];

    public function reservas()
    {
        return $this->hasMany(Reserva::class);
    }
}
