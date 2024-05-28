package juego;

import java.awt.Image;

import entorno.Entorno;
import entorno.Herramientas;

public class Tiranosaurio {
	double x, y;
	Image[] sprites;
	boolean dir; // false = Izq 
	boolean estaApoyado;
	boolean puedeDisparar;
	double escala;
	double alto;
	double ancho;
	int contadorSalto;
	int contadorDisparo;

	
	
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
public boolean disparo(BalaEnemiga ble) {
	if(ble==null && puedeDisparar) {
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
public boolean detectarPrincesa(Tiranosaurio ti, Princesa ba) {
	return (ti.getIzquierdo() < ba.getDerecho() && ti.getDerecho() > ba.getIzquierdo() &&
			ti.getTecho() < ba.getPiso() && ti.getPiso() > ba.getTecho());
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