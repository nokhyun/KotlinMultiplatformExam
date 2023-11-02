import SwiftUI
import shared

struct ContentView: View {
	let pharases = Greeting().greet()

	var body: some View {
        List(pharases, id: \.self) {
            Text($0)
        }
	}
}

struct ContentView_Previews: PreviewProvider {
	static var previews: some View {
		ContentView()
	}
}
