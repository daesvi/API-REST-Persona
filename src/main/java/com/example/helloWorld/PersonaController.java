package com.example.helloWorld;

import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("api/v1/personas")
public class PersonaController {

    private List<Persona> personas = new ArrayList<>();

    @GetMapping()
    public List<Persona> obtenerPersonas() {
        return this.personas;
    }

    @GetMapping("{cedula}")
    public Optional<Persona> obtenerPersonaPorCedula(@PathVariable("cedula") String cedula) {
        return this.personas
                .stream()
                .filter(p -> p.getDocumento().equals(cedula))
                .findFirst();
    }

    @PostMapping()
    public Persona crearPersona(@RequestBody Persona persona) {
        this.personas.add(persona);
        return persona;
    }

    @DeleteMapping("{cedula}")
    public String borrarPersona(@PathVariable("cedula") String cedula){
        // Buscamos la persona con la cédula recibida en el parámetro
        Optional<Persona> personaAEliminar = obtenerPersonaPorCedula(cedula);

        if (personaAEliminar.isPresent()) {
            personas.remove(personaAEliminar.get());
            return "Persona eliminada exitosamente.";
        } else {
            return "Persona no encontrada.";
        }
    }

    @PutMapping("{cedula}")
    public Persona actualizarPersona (@PathVariable("cedula") String cedula,@RequestBody Persona personaActualizada){
        Optional<Persona> personaActualizar = obtenerPersonaPorCedula(cedula);
        if (personaActualizar.isPresent()) {
            // Si encontramos la persona existente, actualizamos sus datos con la persona actualizada
            Persona persona = personaActualizar.get();
            persona.setNombre(personaActualizada.getNombre());
            persona.setApellido(personaActualizada.getApellido());
            persona.setCiudad(personaActualizada.getCiudad());
            return persona;
        } else {
            return null;
        }
    }
}
