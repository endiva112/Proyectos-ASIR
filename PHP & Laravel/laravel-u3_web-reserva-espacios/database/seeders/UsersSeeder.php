<?php

namespace Database\Seeders;

use Illuminate\Database\Seeder;
use Illuminate\Support\Facades\DB;
use Illuminate\Support\Facades\Hash;

class UsersSeeder extends Seeder
{
    public function run(): void
    {
        DB::table('users')->insert([
            [
                'name' => 'Victor',
                'email' => 'victor@centro.edu',
                'password' => Hash::make('password'),
                'dni' => '12345678A',
                'departamento' => 'Ciencias',
                'created_at' => now(),
                'updated_at' => now(),
            ],
            [
                'name' => 'Antonio',
                'email' => 'antonio@centro.edu',
                'password' => Hash::make('password'),
                'dni' => '87654321B',
                'departamento' => 'Tecnología',
                'created_at' => now(),
                'updated_at' => now(),
            ],
        ]);
    }
}
