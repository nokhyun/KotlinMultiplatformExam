import UIKit
import SwiftUI
import ComposeApp

struct ComposeView: UIViewControllerRepresentable {
    
    func makeUIViewController(context: Context) -> UIViewController {
        MainViewControllerKt.MainViewController()
    }

    func updateUIViewController(_ uiViewController: UIViewController, context: Context) {}
}

struct ContentView: View {

    var body: some View {
        ComposeView()
    }

    func log(){

    }
}

struct SplashView: View {
    private let greet: String = GreetingHelper().greet()
    
    var body: some View {
        Text(greet)
        Text("Splash?!")
    }
}


