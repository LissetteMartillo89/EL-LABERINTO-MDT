/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/*
 * lienzo.java
 *
 * Created on 20/11/2012, 10:59:41 AM
 */

package laberinto;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.File;
import java.net.URL;
import javax.imageio.ImageIO;

import javax.swing.JOptionPane;

/**
 *
 * @author Freenjt
 */
public class lienzo extends javax.swing.JPanel implements Runnable{
int [][] matrizGeneral;
    
    /** Creates new form lienzo */
int f=0;
  int radiobotones=0;
    public lienzo() {
        initComponents();
        reinicio();
        
    }
Thread hilo;
     static int e=3,s=4, filas=10,columnas=10;
     int fil_entrada=3, col_entrada=0,filsalida=8,colsalida=9;
int x=0,y=0, termino=0;
BufferedImage personaje,puerta_salida,muro;
URL pers=getClass().getResource("imagenes/npc_boy.png");
URL dor=getClass().getResource("imagenes/doors.png");
URL mu=getClass().getResource("imagenes/pared.png");


public void reinicio(){
    int [][] copia=    {{0,1,0,0,1,0,0,0,0,0},
                        {0,1,0,0,0,0,0,0,0,0},
                        {1,1,0,1,1,0,1,0,0,0},
                        {e,0,0,0,1,0,1,0,0,0},
                        {1,0,0,0,0,0,0,0,0,0},
                        {1,0,1,0,1,0,1,0,0,0},
                        {1,0,1,1,0,0,1,0,1,0},
                        {1,0,0,0,0,1,1,0,1,1},
                        {1,0,1,0,0,1,0,0,0,s},
                        {1,0,1,1,1,1,0,1,1,1}};

    try {
        personaje = ImageIO.read(pers);
        puerta_salida = ImageIO.read(dor);
        muro = ImageIO.read(mu);
    } catch (Exception e) {
        JOptionPane.showMessageDialog(this, "no se cargo imagen por:"+e.getMessage());
    }
  matrizGeneral=copia;
  hilo=new Thread(this);
  fil_entrada=3;
  col_entrada=0;
  filsalida=8;
  colsalida=9;
  f=1;
  termino=0;
  repaint();
}

public void Aleatorio(){

    for(int i=0; i<matrizGeneral.length; i++){
        for(int j=0; j<matrizGeneral.length; j++){
            int dato=(int)(Math.random()*1.99);
            if(i==fil_entrada && j==col_entrada)
            matrizGeneral[i][j]=e;
            else if(i==filsalida && j==colsalida)
            matrizGeneral[i][j]=s;
            else
            matrizGeneral[i][j]=dato;
        }
    }
     //generando camino aleatorio
    int k=fil_entrada,l=col_entrada;
    while(k<filsalida || l<colsalida){
        int camino=(int)(Math.random()*1.9);
        if(camino==0 && colsalida>l)
            matrizGeneral[k][++l]=0;
        else if(filsalida>k)
            matrizGeneral[++k][l]=0;
        else
            matrizGeneral[k][++l]=0;
    }
    matrizGeneral[filsalida][colsalida]=s;
    repaint();
}

public void paint(Graphics g){
    if(f>=1){
    g.setColor(getBackground());
    g.fillRect(0, 0, getWidth(), getHeight());
    for(int i=0; i<matrizGeneral.length; i++){
        for(int j=0; j<matrizGeneral.length; j++){
            g.setColor(Color.BLUE);
            if(matrizGeneral[i][j]==0 || matrizGeneral[i][j]==8)
                g.drawRect(j*40, i*40, 40, 40);
            else  if(matrizGeneral[i][j]==1)
                g.drawImage(muro, j*40, i*40,40,40, this);
            else  if(matrizGeneral[i][j]==e){
                g.drawImage(personaje, j*40, i*40,40,40, this);
               // g.fillOval(j*40, i*40, 40, 40);
            }
            else  if(matrizGeneral[i][j]==s){
                g.drawImage(puerta_salida, j*40, i*40,40,40, this);
               
            }
           else  if(matrizGeneral[i][j]==5){
                 g.setColor(Color.GREEN);
                g.fillRect(j*40, i*40, 40, 40);
                g.setColor(Color.BLUE);
                g.drawRect(j*40, i*40, 40, 40);
            }

        }
    }
    }
}

 public boolean existeCamino(int fila, int columna) {
        if (fila < 0 || fila >= filas || columna < 0 || columna >= columnas) {
            return false;
        }
        if (matrizGeneral[fila][columna] == 5 || matrizGeneral[fila][columna] == 1) {
            return false;
        }
        return true;
    }


 public boolean resolver(int fil, int col) {
      boolean salida = false;
        try {
            Thread.sleep(200);
 } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, ex.getMessage());
        }

        matrizGeneral[fil][col] = 5;


        if (fil == filsalida && col == colsalida) {
            return true;
        }

//abajo
        if (!salida && existeCamino(fil + 1, col)) {
             matrizGeneral[fil+1][col]=e;
            repaint();
            salida = resolver(fil + 1, col);
        }
//derecha
        if (!salida && existeCamino(fil, col + 1)) {
            matrizGeneral[fil][col+1]=e;
            repaint();
            salida = resolver(fil, col + 1);

        }
//izquierda
        if (!salida && existeCamino(fil, col - 1)) {
             matrizGeneral[fil][col-1]=e;
            repaint();
            salida = resolver(fil, col - 1);
        }
//arriba
        if (!salida && existeCamino(fil - 1, col)) {
             matrizGeneral[fil-1][col]=e;
            repaint();
            salida = resolver(fil - 1, col);
        }
/*
        if (!salida) {
            matrizGeneral[fil][col] = 8;
        }

*/

return salida;
    }


    /** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        setBackground(new java.awt.Color(204, 255, 204));
        addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                formMouseClicked(evt);
            }
            public void mousePressed(java.awt.event.MouseEvent evt) {
                formMousePressed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 400, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 300, Short.MAX_VALUE)
        );
    }// </editor-fold>//GEN-END:initComponents

    private void formMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_formMouseClicked

        x=evt.getX()/40;
      y=evt.getY()/40;
      if(matrizGeneral[y][x]!=e && matrizGeneral[y][x]!=s && f==1){

      if(evt.getButton()==evt.BUTTON1 && radiobotones==3 )
      matrizGeneral[y][x]=1;
     else if(evt.getButton()==evt.BUTTON3 && radiobotones==3)
     matrizGeneral[y][x]=0;
     else if(evt.getButton()==evt.BUTTON1 && radiobotones==1){
         matrizGeneral[fil_entrada][col_entrada]=0;
         matrizGeneral[y][x]=e;
         fil_entrada=y;
         col_entrada=x;
     }
      else if(evt.getButton()==evt.BUTTON1 && radiobotones==2){
          matrizGeneral[filsalida][colsalida]=0;
          matrizGeneral[y][x]=s;
          filsalida=y;
          colsalida=x;
      }
      repaint();
      }
    }//GEN-LAST:event_formMouseClicked

    private void formMousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_formMousePressed
      
    }//GEN-LAST:event_formMousePressed

    public void run() {
       if(resolver(fil_entrada,col_entrada)){
       termino=1;
           JOptionPane.showMessageDialog(this, "Felicidades lo lograste");
       
       }
       else{
           termino=1;
          JOptionPane.showMessageDialog(this, "no hay salida");
       }
    }


    // Variables declaration - do not modify//GEN-BEGIN:variables
    // End of variables declaration//GEN-END:variables

}
