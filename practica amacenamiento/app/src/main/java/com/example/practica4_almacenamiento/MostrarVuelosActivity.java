package com.example.practica4_almacenamiento;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.io.FileOutputStream;
import java.io.IOException;

public class MostrarVuelosActivity extends AppCompatActivity {
    private String vueloSeleccionado;
    private String aerolineaSeleccionada;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.actvity_mostrar_vuelos);

        // Inicializar los Spinners y sus adaptadores
        Spinner spinnerVuelos = findViewById(R.id.spinnerVuelos);
        Spinner spinnerAerolineas = findViewById(R.id.spinnerAerolineas);
        ArrayAdapter<CharSequence> vuelosAdapter = ArrayAdapter.createFromResource(
                this, R.array.vuelos_array, android.R.layout.simple_spinner_item);
        ArrayAdapter<CharSequence> aerolineasAdapter = ArrayAdapter.createFromResource(
                this, R.array.aerolineas_array, android.R.layout.simple_spinner_item);
        vuelosAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        aerolineasAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerVuelos.setAdapter(vuelosAdapter);
        spinnerAerolineas.setAdapter(aerolineasAdapter);

        // Manejador para el botón "Guardar Selección"
        Button btnGuardarSeleccion = findViewById(R.id.btnGuardarSeleccion);
        btnGuardarSeleccion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                guardarSeleccion();
            }
        });

        // Manejador para la selección del vuelo
        spinnerVuelos.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                vueloSeleccionado = parent.getItemAtPosition(position).toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                // No hacemos nada
            }
        });

        // Manejador para la selección de la aerolínea
        spinnerAerolineas.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                aerolineaSeleccionada = parent.getItemAtPosition(position).toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                // No hacemos nada
            }
        });
    }

    // Método para guardar la selección en un archivo en la memoria
    private void guardarSeleccion() {
        String seleccion = "Vuelo: " + vueloSeleccionado + ", Aerolínea: " + aerolineaSeleccionada + "\n";
        try {
            FileOutputStream fileOutputStream = openFileOutput("seleccion.txt", MODE_APPEND);
            fileOutputStream.write(seleccion.getBytes());
            fileOutputStream.close();
            Toast.makeText(this, "Selección guardada con éxito.", Toast.LENGTH_SHORT).show();
        } catch (IOException e) {
            e.printStackTrace();
            Toast.makeText(this, "Error al guardar la selección.", Toast.LENGTH_SHORT).show();
        }
    }
}

