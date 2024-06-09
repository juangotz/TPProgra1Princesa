package juego;

import java.awt.Image;

import entorno.Entorno;
import entorno.Herramientas;

public class Bloque {
	private double x, y, alto, ancho, escala;
	private Image spriteR;
	private Image spriteI;
	private boolean rompible; //true = rompible
	
	public Bloque(double x, double y) {
		this.x = x;
		this.y = y;
		rompible = true;
		if(Math.random() > 0.8) {
			rompible = false;
		}
			spriteR = Herramientas.cargarImagen("bloque.jpg");
			spriteI = Herramientas.cargarImagen("bloqueIrrompible.jpg");
	
		
		escala = 0.2;
		alto = spriteR.getHeight(null)*escala;
		ancho = spriteR.getWidth(null)*escala;
	}
	
	public void mostrar(Entorno e) {
		if(rompible) {
			e.dibujarImagen(spriteR, x, y, 0, escala);
		}
		else {
			e.dibujarImagen(spriteI, x, y, 0, escala);
		}
	}
	
	public double getX() {
		return x;
	}

	public void setX(double x) {
		this.x = x;
	}

	public double getY() {
		return y;
	}

	public void setY(double y) {
		this.y = y;
	}

	public double getAlto() {
		return alto;
	}

	public void setAlto(double alto) {
		this.alto = alto;
	}

	public double getAncho() {
		return ancho;
	}

	public void setAncho(double ancho) {
		this.ancho = ancho;
	}

	public double getEscala() {
		return escala;
	}

	public void setEscala(double escala) {
		this.escala = escala;
	}

	public boolean isRompible() {
		return rompible;
	}

	public void setRompible(boolean rompible) {
		this.rompible = rompible;
	}

	public double getTecho(){
		return y - alto/2;
	}
	
	public double getPiso(){
		return y + alto/2;
	}
	
	public double getDerecho(){
		return x + ancho/2;
	}
	
	public double getIzquierdo(){
		return x - ancho/2;
	}

}
