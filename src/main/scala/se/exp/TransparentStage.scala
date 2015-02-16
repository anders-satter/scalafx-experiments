package se.exp

import scalafx.application.JFXApp
import scalafx.scene.Scene
import scalafx.scene.layout.VBox
import scalafx.scene.text.Text
import scalafx.scene.text.Font
import scalafx.Includes._
import scalafx.scene.PerspectiveCamera
import scalafx.scene.transform.Rotate
import scalafx.scene.transform.Translate
import scalafx.scene.Group
import scalafx.scene.Parent
import scalafx.scene.shape.Box
import scalafx.scene.SubScene
import scalafx.scene.paint.Color
import scalafx.scene.PerspectiveCamera.sfxPerspectiveCamera2jfx
import scalafx.scene.SubScene.sfxSubScene2jfx
import scalafx.scene.paint.Color.sfxColor2jfx
import scalafx.scene.shape.Box.sfxBox2jfx
import scalafx.scene.transform.Rotate.sfxRotate2jfx
import scalafx.scene.transform.Translate.sfxTranslate2jfx

object TransparentStage extends JFXApp {
  val text: Text = new Text("Transparent!") {
    font = new Font(20)
  }
  val text2: Text = new Text("Transparent!") {
    font = new Font(20)
  }

  def createContent: Parent = {
    val testBox = new Box(5, 5, 5)
    val camera = new PerspectiveCamera(true) {
      transforms += (
        new Rotate(-20, Rotate.XAxis),
        new Rotate(-20, Rotate.XAxis),
        new Translate(0, 0, -15))
    }
    val root = new Group {
      children ++= Seq(camera, testBox)
    }
    
    val subScene = new SubScene(root, 300, 300)
    subScene.setFill(Color.AliceBlue)
    subScene.setCamera(camera)
    
    new Group {
      children += subScene
    }
    
  }
  val textBox: VBox = new VBox {
    content = Seq(text, text2 //              , 
    //              new Button {
    //            text = "close"
    //            onMouseClicked = {
    //              (e: MouseEvent) =>
    //                {
    //                  stage.close()
    //                }
    //            }
    //          }
    )

    stage = new JFXApp.PrimaryStage {
      //initStyle(StageStyle.TRANSPARENT)
      //title.value = "Timeslicer"
      //width = 300
      //height = 400
      content = createContent
      
      scene = new Scene(createContent){
        resizable = true
      }
      
      
//      scene = new Scene {
//        fill = null
//        root = new Group {
//          children ++= Seq(textBox)
//          //stylesheets += globalTextFormatting
//
//        }
//      }
    }
  }

}