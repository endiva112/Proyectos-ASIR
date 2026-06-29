<?php

use Illuminate\Database\Migrations\Migration;
use Illuminate\Database\Schema\Blueprint;
use Illuminate\Support\Facades\Schema;

return new class extends Migration
{
    public function up(): void
    {
        Schema::table('users', function (Blueprint $table) {
            $table->string('dni')->after('password');           // Añade campo dni después de password
            $table->string('departamento')->after('dni');       // Añade campo departamento después de dni
        });
    }


    public function down(): void
    {
        Schema::table('users', function (Blueprint $table) {
            $table->dropColumn(['dni', 'departamento']);        // Revertir cambios si hacemos rollback
        });
    }
};
