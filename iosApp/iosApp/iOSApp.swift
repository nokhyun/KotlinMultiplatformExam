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
                    NavigationLink(destination: ContentView()
                        .navigationTitle("")
                        .navigationBarHidden(true)) { Text(self.navigateName)}
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

/* 제스쳐 뒤로가기 */
extension UINavigationController: UIGestureRecognizerDelegate {
    open override func viewDidLoad() {
        super.viewDidLoad()
        interactivePopGestureRecognizer?.delegate = self
    }
    
    public func gestureRecognizerShouldBegin(_ gestureRecognizer: UIGestureRecognizer) -> Bool {
        return viewControllers.count > 1
    }
}
