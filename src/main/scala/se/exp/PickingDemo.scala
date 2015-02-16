package se.exp

import scalafx.application.JFXApp
import scalafx.application.JFXApp.PrimaryStage
import scalafx.scene.shape.Box
import scalafx.scene.paint.Color
import scalafx.scene.paint.PhongMaterial
import scalafx.scene.shape.Sphere
import scalafx.scene.Group
import javafx.collections.ObservableList
import scalafx.collections.ObservableBuffer
import scalafx.scene.PointLight
import scalafx.scene.transform.Rotate
import scalafx.scene.SceneAntialiasing
import scalafx.scene.Scene
import scalafx.scene.PerspectiveCamera
import scalafx.beans.property.DoubleProperty
import scalafx.Includes._
import scalafx.scene.input.MouseEvent
import scalafx.scene.shape.DrawMode
import scalafx.scene.control.TextField
import scalafx.scene.control.Button
import javafx.scene.text.TextBoundsType
import com.sun.javafx.geom.BaseBounds.BoundsType

object PickingDemo extends JFXApp {
  
  val textField = new TextField{
    text = "hello"
  }
  //textField.setBoundsType(TextBoundsType.VISUAL); 
  val button = new Button{
    text = "click me"
  }
  
  stage = new PrimaryStage {
    scene = new Scene(500, 500, true, SceneAntialiasing.Balanced) {
      val box = new Box(400, 400, 400) {
        material = new PhongMaterial {
          diffuseColor = Color.Red
          specularColor = Color.Pink
          //diffuseMap takes only an image
          //diffuseMap = textField
          
        }
        drawMode = DrawMode.Line
        content = new Group(textField)
      }

      val sphere = new Sphere(200) {
        material = new PhongMaterial {
          diffuseColor = Color.Blue
          specularColor = Color.LightBlue
        }
        translateZ = -225
        id = "Sphere"
        //content = Seq(textField, button)
          content = new Group(textField, button)
        drawMode = DrawMode.Line
      }

      /*
     * Put shapes in groups so they can be rotated together
     */
      //val shapes = new Group(box, sphere)
          val shapes = new Group {
            children = List(box, sphere)
          }

      val light = new PointLight {
        color = Color.AntiqueWhite
        translateX = -265
        translateY = -260
        translateZ = -625
        rotationAxis = Rotate.YAxis
      }
      
      root = new Group {
        children = new Group(shapes, light)
        translateX = 250
        translateY = 250
        translateZ = 825
        rotationAxis = Rotate.YAxis
      }

      camera = new PerspectiveCamera(false)
      addMouseInteraction(this, shapes)
    }
  }

  def addMouseInteraction(scene: Scene, group: Group) = {
    val angleY = DoubleProperty(-50)
    val yRotate = new Rotate {
      angle <== angleY
      axis = Rotate.YAxis
    }
    var anchorX: Double = 0
    var anchorAngleY: Double = 0
    group.transforms = Seq(yRotate)

    scene.onMousePressed = (event: MouseEvent) => {
      anchorAngleY = angleY()
      /*
       * is the x point of the scene, that the mouse clicked on?
       */
      anchorX = event.sceneX

      /*
       * retrive information about the pick
       *        
       */
      val pickResult = event.pickResult
      pickResult.intersectedNode match {
        case Some(n) =>
          println("Picked node" + n.id() + "'")
          val p = pickResult.intersectedPoint
          group.children += createMarker(x = p.x + n.translateX(),
            y = p.y + n.translateY(), z = p.z + n.translateZ())
           
        case None => println("Picked nothing")
      }
    }
  }

  private def createMarker(x: Double, y: Double, z: Double): Sphere = new Sphere(35) {
    material = new PhongMaterial{
      diffuseColor = Color.Gold
      specularColor = Color.LightGreen      
    }
    translateX = x
    translateY = y
    translateZ = z
  }

}