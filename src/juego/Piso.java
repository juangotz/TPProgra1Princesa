package juego;

import entorno.Entorno;

public class Piso {
	Bloque[] bloques;
	double y;
	
	public Piso(double y) {
		Bloque testigo = new Bloque(0, 0);
		bloques =  new Bloque [(int) (800 / testigo.getAncho()) + 1];
		this.y = y;
		for(int i = 0; i < bloques.length; i++) {
			bloques[i] = new Bloque((i+0.5)* testigo.getAncho(), y);
		}
	}
	
	public void mostrar(Entorno e) {
		for(int i = 0; i < bloques.length; i++) {
			if(bloques[i] != null) {
				bloques[i].mostrar(e);
			}
		}
	}
	

}
