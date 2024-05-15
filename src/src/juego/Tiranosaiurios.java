package juego;

import java.awt.Image;

import entorno.Entorno;
import entorno.Herramientas;

public class Tiranosaiurios {
	double x, y;
	Image spriteIzq;
	Image spriteDer;
	boolean dir; // false = Izq
	boolean estaApoyado;
	double escala;
	double alto;
	double ancho;
	
	public Tiranosaiurios(double x, double y) {
		this.x = x;
		this.y = y;
		spriteIzq = Herramientas.cargarImagen("tiranosaurioizq.png");
		spriteDer = Herramientas.cargarImagen("tiranosaurioDER.png");
		dir = false;
		estaApoyado = false;
		escala = 0.2;
		alto = spriteIzq.getHeight(null) * escala;
		ancho = spriteIzq.getWidth(null) * escala;
	}

public void mostrar(Entorno e) {
	if (dir) {
		e.dibujarImagen(spriteDer, x, y, 0, escala);
	} else {
		e.dibujarImagen(spriteIzq, x, y, 0, escala);
	}
}
}

