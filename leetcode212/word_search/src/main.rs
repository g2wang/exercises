use std::cell::RefCell;
use std::rc::{Rc, Weak};

fn main() {
    println!("Hello, world!");
}

#[derive(Debug)]
struct Node {
    value: char,
    parent: RefCell<Weak<Node>>,
    children: RefCell<Vec<RefCell<Rc<Node>>>>,
    word: RefCell<String>,
}

impl Node {
    #[allow(unconditional_recursion)]
    fn new (c: char) -> Node {
        Node {
            value : c,
            parent: RefCell::new(Rc::downgrade(&Rc::new(Node::new('\u{0}')))),
            children: RefCell::new(vec![RefCell::new(Rc::new(Node::new('\u{0}'))); 26]),
            word: RefCell::new(String::from("")),
        }
    }

    fn add_child(&mut self, c: char) {
        let child_node = Node::new(c);  
        child_node.set_parent(*self);
        let index = (c as u32) - ('a' as u32);
        *self.children.borrow_mut()[index as usize].borrow_mut() = Rc::new(child_node);
    }

    fn set_parent(&mut self, node: Node) {
        *self.parent.borrow_mut() = Rc::downgrade(&Rc::new(node));
    }

    fn set_word(&mut self, w: String) {
        *self.word.borrow_mut() = w;
    }

    fn add_word(&mut self, w: String) {
        let itr = w.chars();
        let c = itr.next();
        let mut node = self;
        while c != None {
            let ch = c.unwrap();
            let index = (ch as u32) - ('a' as u32);
            node = *(node.children.borrow_mut()[index as usize].borrow_mut());
            node.add_child(ch);
            c = itr.next();
        }
        node.set_word(w);
    }
}
