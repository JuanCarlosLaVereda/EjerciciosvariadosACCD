package EjercicioSystem;

public class MiFecha {
    private int dia;
    private int mes;
    private int anyo;

    public MiFecha(int dia, int mes, int anyo) {
        this.dia = dia;
        this.mes = mes;
        this.anyo = anyo;
    }

    public MiFecha(){
        this.dia = 0;
        this.mes = 0;
        this.anyo = 0;
    }

    public boolean esValida(){
        if (dia>0 && dia<=31 && mes>0 && mes<=12){
            if (mes == 4 || mes == 6 || mes == 9 || mes == 11){
                if (dia < 31){
                    return true;
                }
            }
            if (mes==2){
                if (anyo%4 == 0 || (anyo%100==0 && anyo%400==0)){
                    if (dia<30){
                        return true;
                    }
                }
                if (dia<29){
                    return true;
                }
            }
            return false;
        }
        return false;

    }

    public int getDia() {
        return dia;
    }

    public int getMes() {
        return mes;
    }

    public int getAnyo() {
        return anyo;
    }

    public void setDia(int dia) {
        this.dia = dia;
    }

    public void setMes(int mes) {
        this.mes = mes;
    }

    public void setAnyo(int anyo) {
        this.anyo = anyo;
    }

    @Override
    public String toString(){
        return (dia + "/" + mes + "/" + anyo);
    }
}
