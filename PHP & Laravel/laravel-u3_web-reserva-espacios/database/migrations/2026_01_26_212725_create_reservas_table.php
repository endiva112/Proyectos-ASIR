<?php

use Illuminate\Database\Migrations\Migration;
use Illuminate\Database\Schema\Blueprint;
use Illuminate\Support\Facades\Schema;

return new class extends Migration
{
    public function up(): void
    {
        Schema::create('reservas', function (Blueprint $table) {
            $table->id();
            
            // Claves foráneas
            $table->foreignId('user_id')->constrained()->onDelete('cascade'); 
            $table->foreignId('aula_id')->constrained('aulas')->onDelete('cascade');
            $table->foreignId('franja_horaria_id')->constrained('franjas_horarias')->onDelete('cascade');
            
            // Datos de la reserva
            $table->date('fecha');
            $table->string('grupo');
            $table->text('motivo');
            
            $table->timestamps();
        });
    }

    public function down(): void
    {
        Schema::dropIfExists('reservas');
    }
};
