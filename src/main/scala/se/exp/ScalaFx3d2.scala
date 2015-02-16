package se.exp

import scalafx.application.JFXApp
import scalafx.application.JFXApp.PrimaryStage
import scalafx.application.Platform
import scalafx.application.ConditionalFeature
import scalafx.scene.paint.Color
import scalafx.scene.shape.Rectangle
import scalafx.scene.Group
import scalafx.scene.Scene
import scalafx.scene.control.TextField
import scalafx.scene.transform.Rotate
import scalafx.animation.Timeline
import scalafx.scene.control.Slider
import scalafx.geometry.Orientation
import scalafx.scene.layout.HBox
import scalafx.Includes._
import scalafx.geometry.Insets
import scalafx.geometry.Pos
import scalafx.scene.transform.Translate
import scalafx.scene.layout.StackPane

object ScalaFx3d2 extends JFXApp {
  /*
 * Transformations (transforms... )
 * Transforms is the way you take nodes and
 * position them relative to each other
 * 
 * layoutX  layoutY only used in the layout manager
 * translateX, Y, Z (= position)
 * rotate - angle of rotation in degrees, default is just a 2d rotation around the 
 *  Z-axis going straight through the center of your object
 *  But you can specify onother axis
 * scaleX, Y, Z - scales the object, can be done uniformly or different for
 *  the axis which will lead to distorsion of the object
 * transforms - a list of transform objects
 *  Translate
 *  Scale
 *  Rotate
 * 
 * Order of transforms in a node (outside node parent first, the first childnode, etc)
 * Translate(layoutX+translateX, layoutY+translateY, translateZ)
 * Translate(pivotX, Y, Z) // computed center of node
 * Rotate(rotate, rotationAxis)
 * Scale(scaleX, Y, Z)
 * Translate(-pivotX, -Y, -Z) // pivot undone, -> object can be rotated independently from translation
 * transform[0]
 * transform[1]
 * ...
 */
  stage = new PrimaryStage {

    val sliderH = new Slider {
     orientation = Orientation.HORIZONTAL
      min = 0
      max = 180
    }
    
    val sliderV = new Slider {
     orientation = Orientation.HORIZONTAL
      min = 0
      max = 180      
    }
   
    val sliderZ = new Slider {
     orientation = Orientation.HORIZONTAL
      min = 0
      max = 180      
    }
   
    val sliderFade = new Slider {
     orientation = Orientation.HORIZONTAL
      min = 0.0
      max = 0.1      
    }    
    val sliderBox = new HBox {
      padding = Insets(10)
      spacing = 5
      alignment = Pos.TopLeft
      content = Seq(sliderH, sliderV, sliderZ, sliderFade)
    }

    if (!Platform.isSupported(ConditionalFeature.Scene3D)) {
      throw new RuntimeException("3d not supported")
    }

    title = "Depth Buffer Example"
    
    val red = new Rectangle {
      fill = Color.RED
      width = 300
      height = 300
      translateX = -50
      translateY = -50
      translateZ = 1 
      opacity <== sliderFade.value
    }
    val redText = new TextField{
      text="Beckoning..."
      opacity = 1
      
       //= new Color(255, 255, 255)
      //style = "-fx-background-color: rgba(0, 100, 100, 1.0); -fx-background-radius: 10;"
      style = "-fx-background-color: rgba(10, 10, 10, 0.0); -fx-text-inner-color: rgb(200, 200, 200);"
    }
    val redGroup = new Group{
      //style = "-fx-background-color: rgba(0, 100, 100, 1.0); -fx-background-radius: 10;"
      children.add(red)
      children.add(redText)
    }

//    val redPane = new StackPane {
//      style = "-fx-background-color: rgba(0, 100, 100, 0.5); -fx-background-radius: 10;"
//      children.add(red)
//      children.add(redText)
//    }
//    
    
    val green = new Rectangle {
      fill = Color.Green
      width = 300
      height = 300
      translateX = 100
      translateY = 0
      translateZ = 200
    }

    val blue = new Rectangle {
      fill = Color.Blue
      width = 300
      height = 300
      translateX = 0
      translateY = 100
      translateZ = 100
    }

    val rotationGroup = new Group {
      val yRotate = new Rotate(0.0, Rotate.YAxis)
      val xRotate = new Rotate(0.0, Rotate.XAxis)
      val zRotate = new Rotate(0.0, Rotate.ZAxis)
            
      transforms += (new Translate(125, 125, 0.0), yRotate, xRotate, zRotate)
//      translateX = 125
//      translateY = 125
//      rotationAxis = Rotate.YAxis
      children.add(redGroup)
      //children.add(redPane)
      
      children.add(green)
      children.add(blue)
      //tRotate.delegate.setAngle(sliderH.value) <== sliderH.value
      yRotate.angle  <== sliderH.value
      xRotate.angle  <== sliderV.value
      zRotate.angle  <== sliderZ.value
    }

    var animation: Timeline = _

    /*
     * 'true' is the z-buffer flag which gives us 
     * correct 3d object render order.
     * This is the  best way to do it all 
     */
    scene = new Scene(600, 600, true) {
      content = Seq(rotationGroup, sliderBox)
      //fill = Color.AntiqueWhite
      fill = new Color(20, 20, 20)
      
    }
  }
}