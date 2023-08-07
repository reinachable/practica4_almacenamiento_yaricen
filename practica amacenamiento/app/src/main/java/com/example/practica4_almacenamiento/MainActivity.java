package com.example.practica4_almacenamiento;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private ArrayList<String> vuelosReservados;
    private ArrayAdapter<String> adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Inicializar la lista de vuelos reservados
        vuelosReservados = new ArrayList<>();

        // Inicializar el adaptador para la lista de vuelos
        adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, vuelosReservados);

        // Obtener referencia a la ListView y asignar el adaptador
        ListView listViewVuelos = findViewById(R.id.listViewVuelos);
        listViewVuelos.setAdapter(adapter);

        // Manejador para el botón "Mostrar Vuelos"
        Button btnMostrarVuelos = findViewById(R.id.btnMostrarVuelos);
        btnMostrarVuelos.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mostrarVuelos();
            }
        });

        // Manejador para el botón "Registrar Vuelo"
        Button btnRegistrarVuelo = findViewById(R.id.btnRegistrarVuelo);
        btnRegistrarVuelo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                registrarVuelo();
            }
        });

        // Manejador para el botón "Registrar Aerolínea"
        Button btnRegistrarAerolinea = findViewById(R.id.btnRegistrarAerolinea);
        btnRegistrarAerolinea.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                registrarAerolinea();
            }
        });
    }

    // Método para mostrar los vuelos reservados en la lista
    private void mostrarVuelos() {
        Intent intent = new Intent(this, MostrarVuelosActivity.class);
        startActivity(intent);

        // Por ahora, simplemente mostraremos un mensaje si la lista está vacía.
        if (vuelosReservados.isEmpty()) {
            Toast.makeText(this, "No tienes vuelos reservados.", Toast.LENGTH_SHORT).show();
        }
    }

    // Método para registrar un nuevo vuelo
    private void registrarVuelo() {
        Intent intent = new Intent(this, RegistrarVueloActivity.class);
        startActivity(intent);
    }

    // Método para registrar una nueva aerolínea
    private void registrarAerolinea() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Registrar Aerolínea");

        View view = getLayoutInflater().inflate(R.layout.activity_registrar_aerolinea, null);
        final EditText editTextAerolinea = view.findViewById(R.id.editTextAerolinea);
        builder.setView(view);

        builder.setPositiveButton("Guardar", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                String aerolinea = editTextAerolinea.getText().toString().trim();
                if (!aerolinea.isEmpty()) {
                    guardarAerolinea(aerolinea);
                } else {
                    Toast.makeText(MainActivity.this, "Ingrese el nombre de la aerolínea", Toast.LENGTH_SHORT).show();
                }
            }
        });

        builder.setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });

        builder.show();
    }

    private void guardarAerolinea(String aerolinea) {
        String aerolineaTexto = "Aerolínea: " + aerolinea + "\n";

        try {
            FileOutputStream fileOutputStream = openFileOutput("aerolineas.txt", MODE_APPEND);
            fileOutputStream.write(aerolineaTexto.getBytes());
            fileOutputStream.close();
            Toast.makeText(this, "Aerolínea registrada con éxito.", Toast.LENGTH_SHORT).show();
        } catch (IOException e) {
            e.printStackTrace();
            Toast.makeText(this, "Error al guardar la aerolínea.", Toast.LENGTH_SHORT).show();
        }
    }
}