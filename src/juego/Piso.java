package juego;

import entorno.Entorno;

public class Piso {
	Bloque[] bloques;
	double y;
	
	public Piso(double y) {
		bloques =  new Bloque[15];
		this.y = y;
		
		for(int i = 0; i < bloques.length; i++) {
			bloques[i] = new Bloque((i+0.5)*54, y);
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
