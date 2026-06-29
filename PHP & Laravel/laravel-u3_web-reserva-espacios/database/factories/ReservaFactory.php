<?php

namespace Database\Factories;

use App\Models\User;
use App\Models\Aula;
use App\Models\FranjaHoraria;
use Illuminate\Database\Eloquent\Factories\Factory;

class ReservaFactory extends Factory
{
    public function definition()
    {
        return [
            'user_id' => User::inRandomOrder()->first()->id,
            'aula_id' => Aula::inRandomOrder()->first()->id,
            'franja_horaria_id' => FranjaHoraria::inRandomOrder()->first()->id,
            'fecha' => fake()->dateTimeBetween('now', '+2 months')->format('Y-m-d'),
            'grupo' => fake()->randomElement([
                '1º ESO A', '1º ESO B', '2º ESO A', 
                '3º ESO B', '1º Bach A', '2º Bach B'
            ]),
            'motivo' => fake()->sentence(8),
        ];
    }
}
