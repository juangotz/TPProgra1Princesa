package juego;

import java.awt.Image;

import entorno.Entorno;
import entorno.Herramientas;

public class Tiranosaurio {
	double x, y;
	Image spriteIzq;
	Image spriteDer;
	boolean dir; // false = Izq 
	double velocidad;
	boolean estaApoyado;
	boolean estaSaltando; //false = no esta saltando
	double escala;
	double alto;
	double ancho;
	int contadorSalto;
	int contadorDisparo;

	
	
	public Tiranosaurio (double x, double y) {
		this.x = x;
		this.y = y;
		spriteIzq = Herramientas.cargarImagen("tiranosaurioIzq.jpg");
		spriteDer = Herramientas.cargarImagen("tiranosaurioDer.jpg");
		dir = true;
		contadorSalto = 0;
		contadorDisparo = 0;
		estaApoyado = false;
		estaSaltando = false;
		escala = 0.06;
		alto = spriteIzq.getHeight(null) * escala;
		ancho = spriteIzq.getWidth(null) * escala;
	}
	
public void mostrar(Entorno e) {
		
		if (dir) {
			e.dibujarImagen(spriteDer, x, y, 0, escala);
		} else {
			e.dibujarImagen(spriteIzq, x, y, 0, escala);
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
	if(!estaApoyado && !estaSaltando) {
		this.y += 2;
	}
	if (this.dir) {
		this.x += 2;
	}
	if (!this.dir) {
		this.x -= 2;
	}
	if (this.x <= 0) {
		this.dir = true;
	}
	if (this.x >= 800) {
		this.dir = false;
	}
}
public boolean disparo(BalaEnemiga ble) {
	if(ble==null) {
		this.contadorDisparo++;
		if(this.contadorDisparo==30) {
			this.contadorDisparo=0;
			return true;
		}
	}
	return false;
}
public boolean detectarApoyo(Tiranosaurio e, Bloque bl) {
	return Math.abs((e.getPiso() - bl.getTecho())) < 2 && 
			(e.getIzquierdo() < (bl.getDerecho())) &&
			(e.getDerecho() > (bl.getIzquierdo()));		
}


public boolean detectarApoyo(Tiranosaurio e, Piso pi) {
	for(int i = 0; i < pi.bloques.length; i++) {
		if(pi.bloques[i] != null && detectarApoyo(e, pi.bloques[i])) {
			return true;
		}
	}
	
	return false;
}

public boolean detectarApoyo(Tiranosaurio e, Piso[] pisos) {
	for(int i = 0; i < pisos.length; i++) {
		if(detectarApoyo(e , pisos[i])) {
			return true;
		}
	}
	
	return false;
}
public boolean DetectarPared(Tiranosaurio ti, Bloque bl) {
	return (ti.getIzquierdo() < bl.getDerecho() && ti.getDerecho() > bl.getIzquierdo() &&
			ti.getTecho() < bl.getPiso() && ti.getPiso() > bl.getTecho());
}
public boolean detectarPared(Tiranosaurio ti, Piso pi) {
	for(int i = 0; i < pi.bloques.length; i++) {
		if(pi.bloques[i]!= null && DetectarPared(ti, pi.bloques[i])) {
			return true;
		}
	}
	return false;
}

public boolean detectarPared(Tiranosaurio ti, Piso[] pisos) {
	for (int i = 0; i < pisos.length; i++) {
		if(detectarPared(ti, pisos[i])) {
			return true;
		}
	}
	return false;
}
public boolean detectarBala(Tiranosaurio ti, Bala bl) {
	return (ti.getIzquierdo() < bl.getDerecho() && ti.getDerecho() > bl.getIzquierdo() &&
			ti.getTecho() < bl.getPiso() && ti.getPiso() > bl.getTecho());
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