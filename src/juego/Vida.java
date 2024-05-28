package juego;

import java.awt.Image;

import entorno.Entorno;
import entorno.Herramientas;

public class Vida {
	double x, y, escala, ancho, alto;
	Image sprite;
	boolean estaApoyado;
	int contTiempo;

	public Vida(double x, double y) {
		this.x=x;
		this.y=y;
		escala = 0.2;
		estaApoyado = false;
		sprite = Herramientas.cargarImagen("life.png");
		alto = sprite.getHeight(null) * escala;
		ancho = sprite.getWidth(null) * escala;
	}
	public boolean timeOnScreen() {
		this.contTiempo++;
		if (this.contTiempo>210) {
			return true;
		}
		return false;
	}
	public void caer() {
		if(!estaApoyado) {
			this.y += 2;
		}
	}
	public boolean detectarApoyo(Vida v, Bloque bl) {
		return Math.abs((v.getPiso() - bl.getTecho())) < 2 && 
				(v.getIzquierdo() < (bl.getDerecho())) &&
				(v.getDerecho() > (bl.getIzquierdo()));		
	}
	public boolean detectarApoyo(Vida v, Piso pi) {
		for(int i = 0; i < pi.bloques.length; i++) {
			if(pi.bloques[i]!=null && detectarApoyo(v, pi.bloques[i])) {
				return true;
			}
		}
		
		return false;
	}
	
	public boolean detectarApoyo(Vida v, Piso[] pisos) {
		for(int i = 0; i < pisos.length; i++) {
			if(detectarApoyo(v, pisos[i])) {
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
 