import SwiftUI

@main
struct iOSApp: App {
	var body: some Scene {
		WindowGroup {
//			ContentView()
            NavigationView(content: {
                NavigationLink(destination: ContentView()) { /*@START_MENU_TOKEN@*/Text("Navigate")/*@END_MENU_TOKEN@*/ }
            })
		}
	}
}
