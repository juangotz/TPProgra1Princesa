package juego;

import java.awt.Color;
import java.awt.Image;

import entorno.Entorno;
import entorno.Herramientas;

public class Princesa {
	private double x, y;
	private Image [] sprites;
	private boolean dir; // false = Izq
	private boolean estaApoyado;
	private boolean estaSaltando; //false = no esta saltando
	private boolean invul; //true = no recibe daño enemigo
	private double escala;
	private double alto;
	private double ancho;
	private int contadorSalto;
	private int contadorInvul;
	private int vidas;

	public Princesa(double x, double y) {
		this.x = x;
		this.y = y;
		sprites = new Image[4];
		sprites[0] = Herramientas.cargarImagen("pr_idle.png");
		sprites[1] = Herramientas.cargarImagen("pr_idleLeft.png");
		sprites[2] = Herramientas.cargarImagen("pr_jump.png");
		sprites[3] = Herramientas.cargarImagen("pr_jumpLeft.png");
		dir = false;
		contadorSalto = 0;
		contadorInvul = 0;
		estaApoyado = false;
		estaSaltando = false;
		invul = false;
		escala = 0.10;
		alto = sprites[0].getHeight(null) * escala;
		ancho = sprites[0].getWidth(null) * escala;
		vidas = 2;
	}

	public void mostrar(Entorno e) {
		if (!estaApoyado) {
			if (dir) {
				e.dibujarImagen(sprites[2], x, y, 0, escala);
			} else {
				e.dibujarImagen(sprites[3], x, y, 0, escala);
			}
		} else {
			if (dir) {
				e.dibujarImagen(sprites[0], x, y, 0, escala);
			} else {
				e.dibujarImagen(sprites[1], x, y, 0, escala);
			}
		}
		if (this.invul) {
			e.dibujarCirculo(x, y, 30, Color.CYAN);
		}
	}

	public void moverse(boolean dirMov) { // -x == izq, +x == der
			if (dirMov) {
				if (this.x < 800) {
					this.x += 3;
				}
			} else if (this.x > 0){
				this.x -= 3;
			}
			this.dir = dirMov;
	}

	public void movVertical() { // -x == subiendo, +x == bajando
		if (!estaApoyado && !estaSaltando) {
			this.y += 2;
		}

		if(estaSaltando) {
			this.y -= 7;
			contadorSalto++;
		}
		if(contadorSalto == 20) {
			contadorSalto = 0;
			estaSaltando = false;
		}
		
	}
	public void pararSalto() {
		this.estaSaltando=false;
		this.contadorSalto=0;
	}
	public void invulCheck() { // cuenta ticks para darle 1 sec de invulnerabilidad.
		contadorInvul++;
		if (contadorInvul>=60) {
			invul=false;
			contadorInvul=0;
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

	public boolean isDir() {
		return dir;
	}

	public boolean isEstaApoyado() {
		return estaApoyado;
	}
	public void setEstaApoyado(boolean apoyo) {
		this.estaApoyado = apoyo;
	}
	
	public boolean isEstaSaltando() {
		return estaSaltando;
	}
	
	public void setEstaSaltando(boolean salto) {
		this.estaSaltando = salto;
	}
	
	public boolean isInvul() {
		return invul;
	}

	public void setInvul(boolean invul) {
		this.invul = invul;
	}

	public int getVidas() {
		return vidas;
	}

	public void setVidas(int vidas) {
		this.vidas = vidas;
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