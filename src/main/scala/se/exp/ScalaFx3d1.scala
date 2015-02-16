package se.exp

import scalafx.application.JFXApp
import scalafx.application.JFXApp.PrimaryStage
import scalafx.scene.Scene
import scalafx.scene.Group
import scalafx.scene.paint.Color
import scalafx.scene.image.Image
import scalafx.scene.image.ImageView
import scalafx.scene.effect.DropShadow
import scalafx.Includes._

/* Scene graph
   * Hierarchy of group and leaf (graphic primitive) nodes
   * Leaf nodes have individual graphical attributes
   * Group nodes can have collective attributes
   * Parent/child directed graph similar to component trees
   */

/* 4 different node types
   * Basic shapes (rectangle, circle, path, etc)
   * Text (specialized shape, has fonts for instance)
   * ImageView - image, 
   * MediaView - media stream
   */

/*  Stage - in a browser or on the desktop
   *    |
   *  Scene - comp ContentPane, background
   *    |
   *  Root - Root of the graph, in this case a simple group node
   *    |
   *  ImageView   
   * 
   */

object ScalaFx3d1 extends JFXApp {
  stage = new PrimaryStage {
    title = "A simple Scene Graph"
    val root = new Group()
    //val image = new Image(Simple)
    val image = new Image(this.getClass.getResource("/images/Calendar.jpg").toExternalForm)
    val iv = new ImageView(image) {
      fitWidth = 300
      fitHeight = 300
      effect = new DropShadow()
      /*
       * transforms is the way you take nodes and
       * position them relative to each other
       */      
      translateX = 250
      translateY = 250
    }
    /*
     * this is the way to add to children of a group!
     */
    root.children.add(iv)
    /*
     * instantiate the scene attribute and pass the root object 
     * into it
     */
    scene = new Scene(root, 800, 800) {
      fill = Color.Silver
    }
  }
} 