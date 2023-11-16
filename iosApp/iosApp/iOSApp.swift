import SwiftUI

@main
struct iOSApp: App {
    
    @State private var showMainView = false
	var body: some Scene {
		WindowGroup {
            if showMainView {
                NavigationView(content: {
                    NavigationLink(destination: ContentView()) { /*@START_MENU_TOKEN@*/Text("Navigate")/*@END_MENU_TOKEN@*/ }
                })
            }else {
                SplashView()
                    .onAppear{
                        DispatchQueue.main.asyncAfter(deadline: .now() + 0.5){
                            showMainView = true
                        }
                    }
            }
		}
	}
}
