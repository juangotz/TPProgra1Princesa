package juego;

import java.awt.Image;

import entorno.Entorno;
import entorno.Herramientas;

public class Tiranosaurio {
	private double x, y;
	private Image[] sprites;
	private boolean dir; // false = Izq, true = der
	private boolean estaApoyado; // false = no esta apoyado, true = esta apoyado
	private boolean puedeDisparar; // true = puede disparar, false = no puede disparar
	private double escala;
	private double alto;
	private double ancho;
	private int contadorSalto;
	private int contadorDisparo;

	
	
	public Tiranosaurio (double x, double y) {
		this.x = x;
		this.y = y;
		sprites = new Image[4];
		sprites[0] = Herramientas.cargarImagen("tiranosaurioIzq.png");
		sprites[1] = Herramientas.cargarImagen("tiranosaurio.png");
		sprites[2] = Herramientas.cargarImagen("tiranosaurioAltIzq.png");
		sprites[3] = Herramientas.cargarImagen("tiranosaurioAlt.png");
		dir = true;
		contadorSalto = 0;
		contadorDisparo = 0;
		estaApoyado = false;
		puedeDisparar = true;
		escala = 0.06;
		alto = sprites[0].getHeight(null) * escala;
		ancho = sprites[0].getWidth(null) * escala;
	}
	
public void mostrar(Entorno e) {
	if (this.puedeDisparar) {
		if (dir) {
			e.dibujarImagen(sprites[1], x, y, 0, escala);
		} else {
			e.dibujarImagen(sprites[0], x, y, 0, escala);
		}
	} else {
		if (dir) {
			e.dibujarImagen(sprites[3], x, y, 0, escala);
		} else {
			e.dibujarImagen(sprites[2], x, y, 0, escala);
		}
	}
		e.dibujarRectangulo(x, y, ancho, contadorSalto, alto, null);
}
public void changeDir(boolean dirMov) {
	if(dirMov) {
		this.dir = false;	
	}
	if(!dirMov) {
		this.dir = true;
	}
}
public void movimiento() {
	if(!estaApoyado) {
		this.y += 2;
	}
	if (puedeDisparar) {
		if (this.dir) {
			this.x += 2;
		}
		if (!this.dir) {
			this.x -= 2;
		}
	} else {
		if (this.dir) {
			this.x += 6;
		}
		if (!this.dir) {
			this.x -= 6;
		}
	}
	if (this.x <= 0) {
		this.dir = true;
	}
	if (this.x >= 800) {
		this.dir = false;
	}
}
public boolean disparo() {
	if(puedeDisparar) {
		this.contadorDisparo++;
		if(this.contadorDisparo==30) {
			this.contadorDisparo=0;
			return true;
		}
	}
	return false;
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

public boolean isDir() {
	return dir;
}

public void setDir(boolean dir) {
	this.dir = dir;
}

public boolean isEstaApoyado() {
	return estaApoyado;
}

public void setEstaApoyado(boolean estaApoyado) {
	this.estaApoyado = estaApoyado;
}

public boolean isPuedeDisparar() {
	return puedeDisparar;
}

public void setPuedeDisparar(boolean puedeDisparar) {
	this.puedeDisparar = puedeDisparar;
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
