package juego;

import java.awt.Image;

import entorno.Entorno;
import entorno.Herramientas;

public class Gato {
	double x, y, escala, ancho, alto;
	Image sprite;

	public Gato(double x, double y) {
		this.x=x;
		this.y=y;
		escala = 0.2;
		sprite = Herramientas.cargarImagen("gato.png");
		alto = sprite.getHeight(null) * escala;
		ancho = sprite.getWidth(null) * escala;
	}
	
	public boolean detectarApoyo(Gato cat, Bloque bl) {
		return Math.abs((cat.getPiso() - bl.getTecho())) < 2 && 
				(cat.getIzquierdo() < (bl.getDerecho())) &&
				(cat.getDerecho() > (bl.getIzquierdo()));		
	}

	
	public boolean detectarApoyo(Gato cat, Piso pi) {
		for(int i = 0; i < pi.bloques.length; i++) {
			if(detectarApoyo(cat, pi.bloques[i])) {
				pi.bloques[i].rompible=false;
				pi.bloques[(i+1)].rompible=false;
				return true;
			}
		}
		
		return false;
	}
	
	public boolean detectarApoyo(Gato cat, Piso[] pisos) {
		for(int i = 0; i < pisos.length; i++) {
			if(detectarApoyo(cat, pisos[i])) {
				return true;
			}
		}
		
		return false;
	}
	
	public void mostrar(Entorno e) {
		e.dibujarImagen(sprite, x, y, 0, escala);
	}
	public double getTecho() {
		return y - alto / 2;
	}

	public double getPiso() {
		return y + alto / 2;
	}

	public double getDerecho() {
		return x + ancho / 2;
	}

	public double getIzquierdo() {
		return x - ancho / 2;
	}
}
 