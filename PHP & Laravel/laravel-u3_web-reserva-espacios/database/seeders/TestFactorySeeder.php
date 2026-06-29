<?php

namespace Database\Seeders;

use Illuminate\Database\Seeder;
use App\Models\User;
use App\Models\Reserva;

class TestFactorySeeder extends Seeder
{
    /**
     * Generar datos de prueba usando factories
     */
    public function run(): void
    {
        // Crear 15 profesores falsos
        User::factory()->count(15)->create();

        // Crear 80 reservas aleatorias
        Reserva::factory()->count(80)->create();
    }
}