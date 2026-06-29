<?php

use Illuminate\Database\Migrations\Migration;
use Illuminate\Database\Schema\Blueprint;
use Illuminate\Support\Facades\Schema;

return new class extends Migration
{
    public function up(): void
    {
        Schema::create('franjas_horarias', function (Blueprint $table) {
            $table->id();
            $table->string('nombre');          // ej. "08:00 - 09:00"
            $table->tinyInteger('orden');      // para ordenar las franjas
            $table->time('hora_inicio');       // hora de inicio
            $table->time('hora_fin');          // hora de fin
            $table->enum('turno', ['mañana','tarde']); // turno
            $table->timestamps();
        });
    }

    public function down(): void
    {
        Schema::dropIfExists('franjas_horarias');
    }
};
