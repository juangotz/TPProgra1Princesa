package juego;

import entorno.Entorno;

public class GrupoEnemigos {
	public Tiranosaurio[] tiranosaurios;
	public Piso pis;
	
	public GrupoEnemigos(Piso piss) {
		tiranosaurios = new Tiranosaurio[2];
		this.pis = piss;
		
		for(int i = 0; i < tiranosaurios.length; i++) {
			if (i == 0) {
				tiranosaurios[i] = new Tiranosaurio((Math.random()*400), pis.bloques[i].getTecho()-30);
			}
			else {
				tiranosaurios[i] = new Tiranosaurio(((Math.random()*400)+400), pis.bloques[i].getTecho()-30);
			}
		}
	}
	
	public void movimiento() {
		for (int i = 0; i<this.tiranosaurios.length; i++) {
			if(this.tiranosaurios[i]!=null) {
				this.tiranosaurios[i].movimiento();
			}
		}
	}
	
	public void colision(Piso[] p, Bala bl) {
		for (int i = 0; i<this.tiranosaurios.length; i++) {
			if (this.tiranosaurios[i]!=null) {
				if (this.tiranosaurios[i].detectarPared(this.tiranosaurios[i], p)) {
					this.tiranosaurios[i].changeDir(this.tiranosaurios[i].dir);
				}
			if(this.tiranosaurios[i].detectarApoyo(this.tiranosaurios[i], p)) {
				this.tiranosaurios[i].estaApoyado = true;
			}
			else {
				this.tiranosaurios[i].estaApoyado = false;
			}
			if (bl!=null) {
				if (this.tiranosaurios[i].detectarBala(this.tiranosaurios[i], bl)) {
					bl.x += 900;
					this.tiranosaurios[i]=null;
					}
				}
			}
		}
	}
	public void comportamientoAgro(BalaEnemiga ble) {
		for (int i = 0; i<this.tiranosaurios.length; i++) {
			if(this.tiranosaurios[i]!=null) {
				this.tiranosaurios[i].disparo(ble);
			}
		}
	}
	public void mostrar(Entorno e) {
		for (int i = 0; i<this.tiranosaurios.length; i++) {
			if(this.tiranosaurios[i]!=null) {
				this.tiranosaurios[i].mostrar(e);
			}
		}
	}
}
