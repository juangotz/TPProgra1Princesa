package juego;

import java.awt.Image;

import entorno.Entorno;
import entorno.Herramientas;

public class Tiranosaurio {
	double x, y;
	Image spriteIzq;
	Image spriteDer;
	boolean dir; // false = Izq
	boolean estaApoyado;
	boolean estaSaltando; //false = no esta saltando
	boolean puedeMoverse; //true = puede
	double escala;
	double alto;
	double ancho;
	int contadorSalto;
	int momentum;


	public Tiranosaurio (double x, double y) {
		this.x = x;
		this.y = y;
		spriteIzq = Herramientas.cargarImagen("tiranosaurioIzq.jpg");
//		spriteDer = Herramientas.cargarImagen("tiranosaurioDer.jpg");
		dir = false;
		contadorSalto = 0;
		momentum = 0;
		estaApoyado = false;
		estaSaltando = false;
		puedeMoverse = true;
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


//
	
	public void moverse(boolean dirMov) {
		if (estaApoyado) {
				this.momentum = 0;
			}
			this.dir = dirMov;
		}

	public void movVertical() {
		if (!estaApoyado && !estaSaltando) 
				this.momentum= 0;
			}
		
	}

	
	
//}
//
//		if(estaSaltando) {
//			this.y -= 7;
//			contadorSalto++;
//			if(this.momentum==1 && puedeMoverse) {
//				this.x += 1;
//			}
//			if(this.momentum==-1 && puedeMoverse) {
//				this.x -= 1;
//			}
//		}
//		if(contadorSalto == 20) {
//			contadorSalto = 0;
//			estaSaltando = false;
//		}
//		
//	}
//
//	public double getTecho() {
//		return y - alto / 2;
//	}
//
//	public double getPiso() {
//		return y + alto / 2;
//	}
//
//	public double getDerecho() {
//		return x + ancho / 2;
//	}
//
//	public double getIzquierdo() {
//		return x - ancho / 2;
//	}
//
//}
