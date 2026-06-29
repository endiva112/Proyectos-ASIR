<?php

namespace App\Models;

use Illuminate\Database\Eloquent\Model;

class Aula extends Model
{
    protected $fillable = [
        'nombre',
        'descripcion',
    ];

    // Relaciones
    public function reservas()
    {
        return $this->hasMany(Reserva::class);
    }
}
