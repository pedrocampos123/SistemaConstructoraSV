/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.utilidades;

import com.entities.Ubicacion;
import com.teamdev.jxmaps.ControlPosition;
import com.teamdev.jxmaps.InfoWindow;
import com.teamdev.jxmaps.LatLng;
import com.teamdev.jxmaps.Map;
import com.teamdev.jxmaps.MapMouseEvent;
import com.teamdev.jxmaps.MapOptions;
import com.teamdev.jxmaps.MapReadyHandler;
import com.teamdev.jxmaps.MapStatus;
import com.teamdev.jxmaps.MapTypeControlOptions;
import com.teamdev.jxmaps.Marker;
import com.teamdev.jxmaps.MouseEvent;
import com.teamdev.jxmaps.swing.MapView;
import com.vistas.FrmProyecto;

import javax.swing.*;
import java.awt.*;

/**
 * Nombre de la Clase: CreacionMapa 
 * Fecha: 04/11/2020 
 * CopyRight:Pedro Campos
 * modificación: 04/11/2020 
 * Version: 1.1
 * @author pedro
 */
public class CreacionMapa extends MapView {

    public double latitud;
    public double longitud;
    public String nombre;

    String latitudLongitud;
    String nombreUbicacion;
    int validarMarcador = 0;
    
    Mensajeria message = new Mensajeria();

    public CreacionMapa() {                    
        setOnMapReadyHandler(new MapReadyHandler() {
            @Override
            public void onMapReady(MapStatus status) {
                if (status == MapStatus.MAP_STATUS_OK) {
                    final Map map = getMap();    
                    
                    MapOptions options = new MapOptions();
                    MapTypeControlOptions controlOptions = new MapTypeControlOptions();
                    controlOptions.setPosition(ControlPosition.TOP_RIGHT);
                    options.setMapTypeControlOptions(controlOptions);
                    map.setOptions(options);

                    if (latitud == 0.0 && longitud == 0.0) {
                        map.setCenter(new LatLng(13.794185, -88.89653));
                    } else {
                        map.setCenter(new LatLng(latitud, longitud));
                    }

                    /*  Realiza un zoom sobre el mapa de El Salvador
                        coordenadas por defecto: Lat(13.794185), Lng(-88.89653)*/
                    map.setZoom(9.0);

                    Marker marker = new Marker(map);
                    final InfoWindow infoWindow = new InfoWindow(map);
                    map.addEventListener("click", new MapMouseEvent() {
                        @Override
                        public void onEvent(MouseEvent mouseEvent) {
                            //infoWindow.close();
                            final Marker marker = new Marker(map);

                            //captura las coordenadas cuando se da click sobre el mapa
                            latitudLongitud = mouseEvent.latLng().toString();

                            /*Validacion si ya existe un marcador*/
                            switch (validarMarcador) {
                                case 1:
                                    message.printMessageAlerts("¡Ya existe un marcador.\nPor favor eliminar el marcador existente dando click sobre el!", "Mensaje", JOptionPane.WARNING_MESSAGE);
                                    break;
                                default:
                                    /*Permite capturar el nombre que se le dara a la ubicacion*/
                                    nombreUbicacion = message.printImputDialog("¡Ingrese el Nombre de la Ubicacion!");
                                    if (nombreUbicacion.equals("")) {
                                        marker.remove();
                                        validarMarcador = 0;
                                    } else {
                                        //Coloca un nombre sobre el marcador
                                        marker.setPosition(map.getCenter());
                                        infoWindow.setContent(nombreUbicacion);
                                        infoWindow.open(map, marker);
                                        marker.setPosition(mouseEvent.latLng());
                                        validarMarcador = 1;
                                        setearValores();
                                    }
                                    break;
                            }

                            /*evento que permite eliminar el marcador del mapa dando click sobre el
                            * coloca a 0 la variable validar par poder agregar otro marcador
                             */
                            marker.addEventListener("click", new MapMouseEvent() {
                                @Override
                                public void onEvent(MouseEvent mouseEvent) {
                                    marker.remove();
                                    validarMarcador = 0;
                                }
                            });
                        }
                    });
                }
            }
        });
    }

    public void setearValores() {
        //Setear Valores
        Ubicacion location = new Ubicacion();
        String[] langLong = latitudLongitud.replace("[", "").replace("]", "").split(",");
        location.setearDatosMapa(Double.parseDouble(langLong[0]), Double.parseDouble(langLong[1]), nombreUbicacion);
    }
}
