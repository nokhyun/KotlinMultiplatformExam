import SwiftUI
import ComposeApp

@main
struct iOSApp: App {
    
    init() {
        HelperKt.doInitKoin()
        NapierProxyKt.debugBuild()
    }
    
    private let navigateName = "navigate"

    @State private var showMainView = false
	var body: some Scene {
		WindowGroup {
            if showMainView {
                NavigationView(content: {
                    NavigationLink(destination: ContentView()) { Text(self.navigateName)}
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
