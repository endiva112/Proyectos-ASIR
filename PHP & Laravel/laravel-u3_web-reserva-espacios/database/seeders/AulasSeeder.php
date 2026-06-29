<?php

namespace Database\Seeders;

use Illuminate\Database\Seeder;
use Illuminate\Support\Facades\DB;

class AulasSeeder extends Seeder
{
    public function run(): void
    {
        $aulas = [
            [
                'nombre' => 'Salón de actos',
                'descripcion' => 'Espacio amplio para charlas, actos institucionales y presentaciones.',
            ],
            [
                'nombre' => 'Laboratorio de Biología',
                'descripcion' => 'Laboratorio equipado para prácticas de biología.',
            ],
            [
                'nombre' => 'Laboratorio de Química',
                'descripcion' => 'Laboratorio con material para prácticas químicas.',
            ],
            [
                'nombre' => 'Laboratorio de Tecnología',
                'descripcion' => 'Aula técnica con equipos y herramientas tecnológicas.',
            ],
            [
                'nombre' => 'Aula de Informática',
                'descripcion' => 'Aula con ordenadores para clases prácticas.',
            ],
            [
                'nombre' => 'Biblioteca',
                'descripcion' => 'Espacio de estudio y consulta bibliográfica.',
            ],
        ];

        DB::table('aulas')->insert($aulas);
    }
}
