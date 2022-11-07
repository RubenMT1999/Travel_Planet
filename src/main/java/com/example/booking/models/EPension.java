package com.example.booking.models;

public enum EPension {
    SoloAlojamiento("Solo Alojamiento"),
    AlojamientoDesayuno("Alojamiento y Desayuno"),
    MediaPension("Media Pensión"),
    PensionCompleta("Pensión Completa"),
    TodoIncluido("Todo Incluido");



    private final String displayValue;

    private EPension(String displayValue) {
        this.displayValue = displayValue;
    }

    public String getDisplayValue() {
        return displayValue;
    }
}
